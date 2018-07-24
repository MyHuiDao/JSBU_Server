package com.huidao.fishing.game.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.websocket.Session;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.dto.GetOutGoldDto;
import com.huidao.common.entity.FishBaseProperty;
import com.huidao.common.entity.FishControllerProperty;
import com.huidao.common.entity.FishTypeSetting;
import com.huidao.common.entity.GameArea;
import com.huidao.common.entity.GameSetting;
import com.huidao.common.entity.User;
import com.huidao.common.enums.GameType;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IFishOddsService;
import com.huidao.common.interfaces.admin.IFishingRuleService;
import com.huidao.common.interfaces.game.IFishComputeService;
import com.huidao.common.interfaces.game.IGameAreaService;
import com.huidao.common.interfaces.game.IGameFireService;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.game.IGameUserOnlineService;
import com.huidao.common.interfaces.game.IUserGoldLogService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.huidao.fishing.base.message.manager.MessageManager;
import com.huidao.fishing.base.session.SessionManager;
import com.huidao.fishing.game.contants.TypeCode;
import com.huidao.fishing.game.dto.FinalValueGet;
import com.huidao.fishing.game.dto.FireDto;
import com.huidao.fishing.game.dto.FishDto;
import com.huidao.fishing.game.dto.FreeRoomDto;
import com.huidao.fishing.game.dto.RoomDto;
import com.huidao.fishing.game.enums.RoomType;
import com.huidao.fishing.game.util.FishComputeUtil;

/**
 * 房间管理
 * 
 * @author lenovo
 *
 */
@Component
public class RoomManager {

	private static final Logger log = LoggerFactory.getLogger(RoomManager.class);
	private static final String cache_room = "cache_room_id";// 房间id
	private static final String cache_free_room = "cache_free_room_id"; // 空闲房间队列
	private static final String cache_user_id_room_id = "cache_user_id_room_id"; // userId 对应房间id

	@Autowired
	private FishManager fishManager;

	@Reference
	private IUserService userService;

	@Autowired
	private MessageManager messageManager;

	@Autowired
	private SessionManager sessionManager;

	@Reference
	private IGameAreaService gameAreaService;

	@Reference
	private IGameSettingService gameSettingService;

	@Reference
	private IFishOddsService fishOddsService;

	@Reference
	private IFishingRuleService fishingRuleService;

	@Reference
	private IUserGoldLogService userGoldLogService;

	@Reference
	private IGameFireService gameFireService;

	@Reference
	private IFishComputeService fishComputeService;

	@Reference
	private IGameUserOnlineService gameUserOnlineService;

	/**
	 * 创建房间
	 * 
	 * @param userId
	 * @return
	 */
	private void createRoom(String userId, GameArea gat) {
		RoomDto rd = new RoomDto();
		rd.setRoomId(UUID.randomUUID().toString());
		rd.setFishMaxCount(gat.getFishMaxCount());
		rd.setFishMinCount(gat.getFishMinCount());
		rd.setFishProbability(gat.getFishProbability());
		rd.setRoomType(RoomType.valueOf(gat.getType()));
		rd.setGameType(GameType.valueOf(gat.getGameType()));
		rd.setUserId(userId);
		rd.setMultiple(gat.getMultiple());// 房间捕获倍数
		rd.setRoomMaxCount(gat.getRoomMax());// 最大人数4个
		rd.setFireNum(gat.getLevelGold());// 增加一级别 10个金币
		rd.setAreaId(gat.getId());
		rd.getUserList().add(userId);
		rd.getUserFireLevelMap().put(userId, 1);// 默认开炮级别1
		User user = userService.getUserById(userId);
		GetOutGoldDto god = new GetOutGoldDto();
		god.setCostGold(BigDecimal.valueOf(200 * gat.getMultiple()));
		god.setCurrentGold(BigDecimal.valueOf(800 * gat.getMultiple()));
		if (RoomType.experience.equals(rd.getRoomType())) {
			rd.getGetOutGoldMap().put(userId, god);
			rd.getUserGoldMap().put(userId, (long) user.getExperience());
		} else {
			rd.getGetOutGoldMap().put(userId, god);
			Long gold = userService.lockAllGold(user.getId());
			rd.getUserGoldMap().put(userId, gold);
		}
		setRoom(rd);// 保存进缓存
		RedisCache.set(cache_user_id_room_id + userId, rd.getRoomId());
		messageManager.sendMsg(userId, TypeCode.join_game_area, rd);
		updateRoom(rd.getRoomId(), new IRoomUpdate() {
			@Override
			public void update(RoomDto room) {
			}
		}, false, null);
		List<FishDto> fishList = addFish(rd.getRoomId());
		if (fishList.size() == 0) {
			return;
		}
		messageManager.sendMsg(userId, TypeCode.fish_auto, fishList);
	}

	/**
	 * 加入房间
	 * 
	 * @param userId
	 */
	public void addRoom(String userId, String gameAreaId) {
		if (StringUtils.isBlank(gameAreaId)) {
			throw ParamException.param_not_exception;
		}
		GameArea gat = gameAreaService.get(gameAreaId);
		if (gat == null || !"0".equals(gat.getStatus())) {
			throw ParamException.param_exception;
		}
		String rId = RedisCache.get(cache_user_id_room_id + userId);
		if (StringUtils.isNotBlank(rId)) {
			exitRoom(userId, rId, false);
		}
		User user = userService.getUserById(userId);
		if (RoomType.gold.toString().equals(gat.getType())) {
			if (user.getGold() < gat.getMinNum()) {
				messageManager.sendMsg(userId, TypeCode.user_no_gold, "金币不足");
				return;
			}
		}
		if (RoomType.experience.toString().equals(gat.getType())) {
			if (user.getExperience() < gat.getMinNum()) {
				messageManager.sendMsg(userId, TypeCode.user_no_experience, "体验币不足");
				return;
			}
		}
		gameUserOnlineService.startGame(GameType.valueOf(gat.getGameType()), user.getId());
		RedisCache.lock(cache_free_room + gameAreaId);
		FreeRoomDto frd = RedisCache.get(cache_free_room + gameAreaId, FreeRoomDto.class);
		if (frd == null) {
			frd = new FreeRoomDto();
			RedisCache.set(cache_free_room + gameAreaId, frd);
		}

		if (frd.getRoomIdList().size() == 0) {
			RedisCache.unlock(cache_free_room + gameAreaId);
			createRoom(userId, gat);
			return;
		}

		String roomId = frd.getRoomIdList().get(0);
		RoomDto rd = updateRoom(roomId, new IRoomUpdate() {
			@Override
			public void update(RoomDto room) {
				User user = userService.getUserById(userId);
				Map<String, Object> map = new HashMap<>();
				map.put("userId", user.getId());

				if (RoomType.experience.equals(room.getRoomType())) {
					room.getUserGoldMap().put(userId, user.getExperience());
					map.put("gold", user.getExperience());
					GetOutGoldDto god = userGoldLogService.getGetOutExperience(userId);
					room.getGetOutGoldMap().put(userId, god);
				} else {
					GetOutGoldDto god = userGoldLogService.getGetOutGold(userId);
					Long gold = userService.lockAllGold(user.getId());
					room.getUserGoldMap().put(userId, gold);
					map.put("gold", gold);
					room.getGetOutGoldMap().put(userId, god);
				}
				room.getUserFireLevelMap().put(userId, 1);// 默认开炮级别1
				RedisCache.set(cache_user_id_room_id + userId, roomId);
				room.addRoom(userId);
				sendMsg(room, TypeCode.noice_user_add_room, map);
			}
		}, true, frd);
		for (String userId1 : rd.getUserList()) {
			if (StringUtils.isBlank(userId)) {
				continue;
			}
			if (!rd.getUserIsAi().contains(userId1)) {
				String str = RedisCache.get(CacheContants.user_last_request_time + userId1);
				if (StringUtils.isBlank(str)) {
					exitRoom(userId1, rd.getRoomId(), true);
				}
			}
		}
		messageManager.sendMsg(userId, TypeCode.join_game_area, rd);

	}

	/**
	 * 退出房间
	 * 
	 * @param roomId
	 */
	public void exitRoom(String userId, String roomId, boolean flag1) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(roomId)) {
			throw ParamException.param_exception;
		}
		RedisCache.lock(cache_room + roomId);
		RoomDto rd = RedisCache.get(cache_room + roomId, RoomDto.class);
		if (rd == null) {
			RedisCache.remove(cache_user_id_room_id + userId);
			return;
		}
		if (flag1) {
			sendMsg(rd, TypeCode.exit_room, userId);
		} else {
			sendMsgNoSelf(userId, rd, TypeCode.exit_room, userId);
		}
		if (rd.getGameType() == null) {
			rd.setGameType(GameType.fishing);
		}
		gameUserOnlineService.endGame(rd.getGameType(), userId);
		try {
			log.error("游戏结束-进行结算：" + JSONObject.toJSONString(rd));
			Long gold = rd.getUserGoldMap().get(userId);
			gold = gold == null ? 0L : gold;
			MsgDto<String> msg = null;
			RedisCache.remove(cache_user_id_room_id + userId);
			if (RoomType.gold.equals(rd.getRoomType())) {
				msg = userService.unLockGold(userId, gold, rd.getGameType());
			} else if (RoomType.experience.equals(rd.getRoomType())) {
				msg = userService.setExperience(userId, gold);
			}
			if (MsgFactory.isFail(msg)) {
				RedisCache.set(cache_user_id_room_id + userId, rd.getRoomId());
				log.error("结算出错：{}", JSONObject.toJSON(msg));
				return;
			}
			if (rd.getRoomFreeBit() == 1) {
				RedisCache.lock(cache_free_room + rd.getAreaId());
				FreeRoomDto frd = RedisCache.get(cache_free_room + rd.getAreaId(), FreeRoomDto.class);
				for (String id : frd.getRoomIdList()) {
					if (id.equals(rd.getRoomId())) {
						frd.getRoomIdList().remove(id);
						RedisCache.set(cache_free_room + rd.getAreaId(), frd);
						break;
					}
				}
				RedisCache.remove(cache_room + roomId);
				RedisCache.unlock(cache_free_room + rd.getAreaId());
			} else {
				RedisCache.lock(cache_free_room + rd.getAreaId());
				FreeRoomDto frd = RedisCache.get(cache_free_room + rd.getAreaId(), FreeRoomDto.class);
				boolean flag = false;
				for (String id : frd.getRoomIdList()) {
					if (id.equals(rd.getRoomId())) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					frd.getRoomIdList().add(roomId);
					RedisCache.set(cache_free_room + rd.getAreaId(), frd);
				}
				for (int i = 0; i < rd.getUserList().size(); i++) {
					if (userId.equals(rd.getUserList().get(i))) {
						rd.getUserList().set(i, null);
						break;
					}
				}
				if (rd.getUserId().equals(userId)) {
					for (int i = 0; i < rd.getUserList().size(); i++) {
						if (rd.getUserList().get(i) != null && !rd.getUserIsAi().contains(rd.getUserList().get(i))) {
							rd.setUserId(rd.getUserList().get(i));
							messageManager.sendMsg(rd.getUserId(), TypeCode.main_client_change);
							break;
						}
					}
				}
				RedisCache.set(cache_room + roomId, rd);
				RedisCache.unlock(cache_free_room + rd.getAreaId());
			}
			RedisCache.unlock(cache_room + roomId);
		} catch (Exception e) {
			log.error("返回用户{},金币出错共{},{}", userId, rd.getUserGoldMap().get(userId), e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加鱼
	 * 
	 * @param roomId
	 */
	public List<FishDto> addFish(String roomId) {
		List<FishDto> fishList = new ArrayList<>();
		updateRoom(roomId, new IRoomUpdate() {
			@Override
			public void update(RoomDto room) {
				if (room.isGroupTime()) {
					return;
				}
				int size = room.getFishMap().size();
				if (size == 0) {
					fishList.addAll(fishManager.init());
					for (FishDto fd : fishList) {
						room.getFishMap().put(fd.getId(), fd);
					}
				} else {
					fishManager.addFish(null, fishList, room.getFishMap(), room.getFishMaxCount());
				}
			}
		}, false, null);
		return fishList;
	}

	/**
	 * 用户id获取房间id
	 * 
	 * @param userId
	 * @return
	 */
	public String getRoomId(String userId) {
		return RedisCache.get(cache_user_id_room_id + userId);
	}

	/**
	 * 房间解锁
	 * 
	 * @param roomId
	 */
	public static void unLockRoom(String roomId) {
		RedisCache.unlock(cache_room + roomId);
	}

	/**
	 * 房间上锁
	 * 
	 * @param roomId
	 */
	public static void lockRoom(String roomId) {
		RedisCache.lock(cache_room + roomId);
	}

	/**
	 * 房间id获取房间对象
	 * 
	 * @param roomId
	 * @return
	 */
	public static RoomDto getRoom(String roomId) {
		return RedisCache.get(cache_room + roomId, RoomDto.class);
	}

	public static void setRoom(RoomDto room) {
		RedisCache.set(cache_room + room.getRoomId(), room);
	}

	/**
	 * 更新房间内数据
	 * 
	 * @param userChange
	 *            是否在外层锁死 匹配队列
	 * @param roomId
	 * @param ru
	 */
	public RoomDto updateRoom(String roomId, IRoomUpdate ru, boolean isLockFreeRoom, FreeRoomDto frd) {
		RoomDto room = null;
		try {
			lockRoom(roomId);
			room = getRoom(roomId);
			ru.update(room);
			setRoom(room);
			if (!isLockFreeRoom) {
				if (frd == null) {
					RedisCache.lock(cache_free_room + room.getAreaId());
					frd = RedisCache.get(cache_free_room + room.getAreaId(), FreeRoomDto.class);
				}
				if (frd == null) {
					frd = new FreeRoomDto();
				}
				if (room.getRoomFreeBit() >= room.getRoomMaxCount() || room.isGroupTime()) {
					List<String> roomIdList = frd.getRoomIdList();
					for (int i = 0; i < roomIdList.size(); i++) {
						if (roomId.equals(roomIdList.get(i))) {
							roomIdList.remove(i);
							RedisCache.set(cache_free_room + room.getAreaId(), frd);
							break;
						}
					}

				} else {
					boolean isExist = false;
					List<String> roomIdList = frd.getRoomIdList();
					for (int i = 0; i < roomIdList.size(); i++) {
						if (roomId.equals(roomIdList.get(i))) {
							isExist = true;
							break;
						}
					}
					if (!isExist) {
						roomIdList.add(roomId);
						RedisCache.set(cache_free_room + room.getAreaId(), frd);
					}
				}
				RedisCache.unlock(cache_free_room + room.getAreaId());
			}
			return room;
		} catch (Exception e) {
			throw new RuntimeException("操作房间数据出错," + e.getMessage());
		} finally {
			unLockRoom(roomId);
			if (isLockFreeRoom) {
				RedisCache.unlock(cache_free_room + room.getAreaId());
			}
		}
	}

	public void addFrie(String userId, Double angle) {
		String roomId = getRoomId(userId);
		if (StringUtils.isBlank(roomId)) {
			throw ParamException.param_not_exception;
		}
		FireDto fd = new FireDto();
		fd.setAngle(angle);
		fd.setUserId(userId);
		Map<String, Object> map = new HashMap<>();
		RoomDto room = updateRoom(roomId, new IRoomUpdate() {
			@Override
			public void update(RoomDto room) {
				fd.setLevel(room.getUserFireLevelMap().get(userId));
				int sum = fd.getLevel() * room.getFireNum();
				long outGold = room.getUserGoldMap().get(userId) - sum;
				if (outGold >= 0) {
					GetOutGoldDto getOutGoldDto = room.getGetOutGoldMap().get(userId);
					getOutGoldDto.setCostGold(getOutGoldDto.getCostGold().add(BigDecimal.valueOf(sum)));
					map.put("useGold", sum);
					room.getUserGoldMap().put(userId, outGold);
					room.getFireMap().put(fd.getId(), fd);
					room.setGoldGet(room.getGoldGet() + sum);
					map.put("sumGold", outGold);
				}
			}
		}, false, null);
		map.put("fire", fd);
		if (!map.containsKey("useGold")) {
			if (room.getRoomType().equals(RoomType.gold)) {
				messageManager.sendMsg(userId, TypeCode.no_gold_fire, "金币不足");
			} else {
				messageManager.sendMsg(userId, TypeCode.no_experience_fire, "体验币不足");
			}
			return;
		}

		sendMsg(room, TypeCode.open_fire, map);
	}

	/**
	 * @param id
	 *            炮id
	 * @param fishIdList
	 *            击中的鱼群
	 * @param session
	 * @param x
	 *            炮消失的x点
	 * @param y
	 *            炮消失的y点
	 */
	public void deleteFire(Session session, Double x, Double y, String id, String[] fishIdList) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		String currentUserId = sessionManager.getUserId(session);
		String roomId = getRoomId(currentUserId);
		if (StringUtils.isBlank(roomId)) {
			throw ParamException.param_exception;
		}
		List<String> fishList = new ArrayList<>();
		FinalValueGet fvg = new FinalValueGet();
		Map<String, Integer> fishGold = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		RoomDto room = updateRoom(roomId, new IRoomUpdate() {
			@Override
			public void update(RoomDto room) {
				FireDto fire = room.getFireMap().get(id);
				if (fire == null) {
					throw ParamException.param_exception;
				}
				if (!currentUserId.equals(fire.getUserId())) {
					throw ParamException.param_exception;
				}
				if (fishIdList != null && fishIdList.length > 0) {
					GetOutGoldDto ggd = room.getGetOutGoldMap().get(currentUserId);
					FishBaseProperty fpd = fishComputeService.getFishBaseProperty(room.getAreaId(), currentUserId);
					FishControllerProperty fcpd = fishComputeService.getFishControllerProperty(room.getAreaId(),
							currentUserId);
					for (int i = 0; i < fishIdList.length; i++) {
						FishDto fd = room.getFishMap().get(fishIdList[i]);
						if (fd == null) {
							continue;
						}
						FishTypeSetting fnt = fishManager.getFishTypeSettingMap().get(fd.getLevel());
						fcpd.setRoomMultiple(room.getMultiple());
						fcpd.setFishLevel(fnt.getLevel());
						fcpd.setFireLevel(fire.getLevel());
						double catch1 = FishComputeUtil.getCatch(fpd, fcpd, fnt, ggd, room);
						if (ThreadLocalRandom.current().nextDouble(100) <= catch1 && catch1 > 0) {
							room.getFishMap().remove(fd.getId());
							int getOutGold = fnt.getGold() * fire.getLevel() * room.getMultiple();
							fishList.add(fd.getId());
							fvg.setGold(fvg.getGold() + getOutGold);
							fishGold.put(fd.getId(), getOutGold);
							room.getUserGoldMap().put(currentUserId,
									room.getUserGoldMap().get(currentUserId) + getOutGold);
							if (StringUtils.isNotBlank(fd.getGroup())) {
								room.setGroupTime(true);
								if (room.getFishMap().size() == 0) {
									room.setGroupTime(false);
								}
							}
							room.setGoldOut(room.getGoldOut() + getOutGold);
							ggd.setCurrentGold(ggd.getCurrentGold().add(BigDecimal.valueOf(getOutGold)));
						}

					}

				}
				room.getFireMap().remove(id);
				map.put("sumGold", room.getUserGoldMap().get(currentUserId));
			}
		}, false, null);
		map.put("fireId", id);// 炮id
		map.put("x", x);
		map.put("y", y);
		map.put("fishList", fishList);
		map.put("fishGold", fishGold);
		map.put("getGold", fvg.getGold());

		sendMsg(room, TypeCode.fire_delete, map);
		List<FishDto> fishList1 = addFish(room.getRoomId());
		if (fishList1.size() == 0) {
			return;
		}
		sendMsg(room, TypeCode.fish_auto, fishList1);
	}

	/**
	 * 鱼自然消失
	 * 
	 * @param session
	 * @param id
	 */
	public void deleteFish(Session session, String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		String currentUserId = sessionManager.getUserId(session);
		String roomId = getRoomId(currentUserId);
		if (StringUtils.isBlank(roomId)) {
			throw ParamException.param_exception;
		}
		RoomDto room = updateRoom(roomId, new IRoomUpdate() {
			@Override
			public void update(RoomDto room) {
				if (!room.getUserId().equals(currentUserId)) {
					// 鱼消失不是主机判断的
					throw ParamException.param_exception;
				}
				FishDto fish = room.getFishMap().get(id);
				if (fish == null) {
					// 鱼不存在
					System.err.println("鱼不存在----------------------");
					throw ParamException.param_exception;
				}
				room.getFishMap().remove(id);
				if (StringUtils.isNotBlank(fish.getGroup())) {
					room.setGroupTime(true);
					if (room.getFishMap().size() == 0) {
						room.setGroupTime(false);
					}
				}
			}
		}, false, null);
		sendMsg(room, TypeCode.fish_delete, id);
		List<FishDto> fishList = addFish(room.getRoomId());
		if (fishList.size() == 0) {
			return;
		}
		sendMsg(room, TypeCode.fish_auto, fishList);
	}

	public void sendMsg(RoomDto room, String code, Object data) {
		List<String> userList = room.getUserList();
		for (String userId : userList) {
			if (StringUtils.isBlank(userId)) {
				continue;
			}
			if (!room.getUserIsAi().contains(userId)) {
				messageManager.sendMsg(userId, code, data);
			}
		}
	}

	public void sendMsgNoSelf(String noUserId, RoomDto room, String code, Object data) {
		List<String> userList = room.getUserList();
		for (String userId : userList) {
			if (StringUtils.isBlank(userId)) {
				continue;
			}
			if (!room.getUserIsAi().contains(userId) && !userId.equals(noUserId)) {
				messageManager.sendMsg(userId, code, data);
			}
		}
	}

	public void sendMsg(RoomDto room, String code) {
		List<String> userList = room.getUserList();
		for (String userId : userList) {
			if (StringUtils.isBlank(userId)) {
				continue;
			}
			if (!room.getUserIsAi().contains(userId)) {
				messageManager.sendMsg(userId, code);
			}
		}
	}

	public static void unLockFree(String areaId) {
		RedisCache.unlock(cache_free_room + areaId);
	}

	/**
	 * 添加渔阵
	 * 
	 * @param room
	 */
	public void addFishGroup(RoomDto room) {
		updateRoom(room.getRoomId(), new IRoomUpdate() {
			@Override
			public void update(RoomDto room) {
				if (room.isGroupTime()) {
					return;
				}
				room.getFishList().clear();
				room.getFishMap().clear();
				sendMsg(room, TypeCode.game_clear_fish);
				room.setGroupStartDate(new Date());
				List<FishDto> list = fishManager.fishArray();
				room.setGroupTime(true);
				for (FishDto fish : list) {
					room.getFishMap().put(fish.getId(), fish);
				}
				sendMsg(room, TypeCode.game_fish_array_generate, room.getFishList());
			}
		}, false, null);
	}

	/**
	 * 提示鱼阵到来时间
	 */
	public void fishArrayPrompt(RoomDto roomDto) {
		// 1.获取当前时间
		Date current = new Date();
		// 2.获取鱼阵时间
		GameSetting gameSetting = gameSettingService.getKeyGameSetting(GameSettingContants.min_fish_group);
		Long fishArray = Long.parseLong(gameSetting.getValue());
		// 3.获取房间开始时间
		Date room = roomDto.getGroupStartDate();
		Long difference = (current.getTime() - room.getTime()) / 1000;

		if (fishArray - difference < 0) {
			sendMsg(roomDto, TypeCode.fish_array_date, 0);
			return;
		}
		sendMsg(roomDto, TypeCode.fish_array_date, fishArray - difference);
	}

	public void supplementFish(Session session) {
		String currentUserId = sessionManager.getUserId(session);
		String roomId = getRoomId(currentUserId);
		RoomDto room = getRoom(roomId);
		updateRoom(roomId, new IRoomUpdate() {
			@Override
			public void update(RoomDto room) {
				if (room.isGroupTime()) {
					room.setGroupTime(false);
					room.getFishMap().clear();
				}
			}
		}, false, null);
		List<FishDto> fishList = addFish(room.getRoomId());
		if (fishList.size() == 0) {
			return;
		}
		sendMsg(room, TypeCode.fish_auto, fishList);
	}

}
