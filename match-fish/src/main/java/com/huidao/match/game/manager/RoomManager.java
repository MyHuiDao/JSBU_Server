package com.huidao.match.game.manager;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.entity.MatchFishBets;
import com.huidao.common.entity.MatchFishOpenPrize;
import com.huidao.common.entity.User;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.interfaces.match.fish.IMatchFishBetsService;
import com.huidao.common.interfaces.match.fish.IMatchFishOpenPrizeService;
import com.huidao.common.interfaces.match.fish.IOpenPrizeService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.huidao.match.base.message.manager.MessageManager;
import com.huidao.match.game.contants.TypeCode;
import com.huidao.match.game.dto.FreeRoomDto;
import com.huidao.match.game.dto.RoomDto;
import com.huidao.match.game.enums.RoomStatus;

@Component
public class RoomManager {

	private static final Logger log = LoggerFactory.getLogger(RoomManager.class);

	@PostConstruct
	private void startOpenPrizeThread() {
		new NoticeRoomThread(this, messageManager, openPrizeService, gameSettingService).start();
	}

	public static final String raceFish_room = "raceFish_room_id";// 房间id
	private static final String raceFish_free_room = "raceFish_free_room_id"; // 空闲房间队列
	private static final String raceFish_user_id_room_id = "raceFish_user_id_room_id"; // userId 对应房间id

	@Autowired
	private MessageManager messageManager;

	@Reference
	private IOpenPrizeService openPrizeService;

	@Reference
	private IMatchFishOpenPrizeService matchFishOpenPrizeService;

	@Reference
	private IMatchFishBetsService matchFishBetsService;

	@Reference
	private IUserService userService;

	@Reference
	private IGameSettingService gameSettingService;

	private void createRoom(FreeRoomDto frd, String userId) {
		RoomDto roomDto = new RoomDto();
		String roomNumber = RedisCache.get("room_increased");
		while (true) {
			Integer num = (int) ThreadLocalRandom.current().nextLong(900000) + 100000;
			if (StringUtils.isNotBlank(roomNumber)) {
				num = (int) ThreadLocalRandom.current().nextLong(900000) + 100000;
				roomDto.setRoomId(num.toString());
			} else {
				roomDto.setRoomId(num.toString());
			}
			RedisCache.set("room_increased", num);
			RedisCache.lock(raceFish_room + roomDto.getRoomId());
			if (RedisCache.get(raceFish_room + roomDto.getRoomId()) == null) {
				break;
			}
			RedisCache.unlock(raceFish_room + roomDto.getRoomId());
		}
		roomDto.setStatus(RoomStatus.bets);
		roomDto.getUserLists().add(userId);
		roomDto.setCreateTime(new Date().getTime());
		String id = matchFishOpenPrizeService.insterMatchFishOpenPrize(roomDto.getRoomId());
		roomDto.setLotteryTime(System.currentTimeMillis());
		roomDto.setOpenPrezeId(id);
		roomDto.setSeconds(Long.parseLong(gameSettingService.getValue(GameSettingContants.match_fish_seconds)));
		roomDto.setOnlineUsers(roomDto.getUserLists().size());
		// 设置房间最大人数
		roomDto.setMaxUser(
				Integer.valueOf(gameSettingService.getValue(GameSettingContants.match_fish_room_people_number)));
		RedisCache.set(raceFish_user_id_room_id + userId, roomDto.getRoomId());
		RedisCache.set(raceFish_room + roomDto.getRoomId(), roomDto);
		frd.getRoomIdList().add(roomDto.getRoomId());
		RedisCache.unlock(raceFish_room + roomDto.getRoomId());
		RedisCache.unlock(raceFish_free_room);
		messageManager.sendMsg(userId, TypeCode.add_room, roomDto);
	}

	public void exitRoom(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		// 1.通过redis获取用户所在房间
		String roomId = RedisCache.get(raceFish_user_id_room_id + userId);
		if (StringUtils.isBlank(roomId)) {
			return;
		}
		RedisCache.lock(raceFish_room + roomId);
		try {
			// 2.用户退出房间
			if (StringUtils.isBlank(roomId)) {
				throw ParamException.param_not_exception;
			}
			RedisCache.remove(raceFish_user_id_room_id + userId);
			RoomDto rd = RedisCache.get(raceFish_room + roomId, RoomDto.class);
			for (String str : rd.getUserLists()) {
				if (str.equals(userId)) {
					rd.getUserLists().remove(str);
					break;
				}
			}
			RedisCache.set(raceFish_room + roomId, rd);
			if (rd.getUserLists().size() <= 0) {
				RedisCache.remove(raceFish_room + roomId);
				RedisCache.lock(raceFish_free_room);
				FreeRoomDto frd = RedisCache.get(raceFish_free_room, FreeRoomDto.class);
				for (int i = 0; i < frd.getRoomIdList().size(); i++) {
					if (roomId.equals(frd.getRoomIdList().get(i))) {
						frd.getRoomIdList().remove(i);
						break;
					}
				}
				RedisCache.set(raceFish_free_room, frd);
				RedisCache.unlock(raceFish_free_room);
			}
			messageManager.sendMsg(userId, TypeCode.exit_room, "退出成功");
			sendMsg(rd, TypeCode.exit_room_all, rd.getUserLists().size());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			RedisCache.unlock(raceFish_room + roomId);
		}
	}

	public void bets(String userId, String betsNumber, String betsGold) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		String roomId = RedisCache.get(raceFish_user_id_room_id + userId);
		if (StringUtils.isBlank(roomId)) {
			throw ParamException.param_exception;
		}
		RoomDto roomDto = getRoomById(roomId);

		if (roomDto.getSeconds() < 10) {
			messageManager.sendMsg(userId, TypeCode.bets_fail, "当前不在下单时间内");
			return;
		}

		if (StringUtils.isBlank(betsNumber)) {
			throw ParamException.param_not_exception;
		}
		String[] number = betsNumber.split(",");
		if (number.length == 2) {
			if (number[0].equals(number[1])) {
				messageManager.sendMsg(userId, TypeCode.bets_fail, "不允许购买同号组合");
			}
		}
		if (number.length > 2) {
			throw ParamException.param_exception;
		}
		if (StringUtils.isBlank(betsGold)) {
			throw ParamException.param_not_exception;
		}
		MatchFishBets matchFishBets = new MatchFishBets();
		matchFishBets.setUserId(userId);
		matchFishBets.setOpenPrizeId(roomDto.getOpenPrezeId());
		matchFishBets.setBetsNumber(betsNumber);
		matchFishBets.setBetsGold(Integer.parseInt(betsGold));
		matchFishBets.setBetsDate(new Date());
		matchFishBets.setStatus(0);
		MsgDto<Integer> msgDto = matchFishBetsService.saveMatchFishBets(matchFishBets);
		if (MsgFactory.isFail(msgDto)) {
			messageManager.sendMsg(userId, TypeCode.bets_fail, msgDto.getMsg());
			return;
		}
		messageManager.sendMsg(userId, TypeCode.bets, matchFishBets);
	}

	// 加入房间
	public void addRoom(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		// 获取房间id
		String id = RedisCache.get(raceFish_user_id_room_id + userId);
		// 如果房间已存在则加入房间
		if (StringUtils.isNotBlank(id)) {
			RoomDto rd = RedisCache.get(raceFish_room + id, RoomDto.class);
			messageManager.sendMsg(userId, TypeCode.add_room, rd);
			return;
		}
		// 上锁
		RedisCache.lock(raceFish_free_room);
		FreeRoomDto frd = RedisCache.get(raceFish_free_room, FreeRoomDto.class);
		// 如果为空初始化队列
		if (frd == null) {
			frd = new FreeRoomDto();
			RedisCache.set(raceFish_free_room, frd);
		}

		// 如果队列中为空
		if (frd.getRoomIdList().size() == 0) {
			createRoom(frd, userId);
			RedisCache.set(raceFish_free_room, frd);
			RedisCache.unlock(raceFish_free_room);
			return;
		}
		// 获取队列中的房间id
		String roomId = frd.getRoomIdList().get(0);
		// 上锁
		RedisCache.lock(raceFish_room + roomId);
		// 获取房间信息
		RoomDto rd = RedisCache.get(raceFish_room + roomId, RoomDto.class);
		// 添加到房间用户列表
		rd.getUserLists().add(userId);

		if (rd.getUserLists().size() >= rd.getMaxUser()) {
			frd.getRoomIdList().remove(0);
			RedisCache.set(raceFish_free_room, frd);
		}
		rd.setOnlineUsers(rd.getUserLists().size());
		RedisCache.set(raceFish_room + roomId, rd);
		RedisCache.unlock(raceFish_room + roomId);
		RedisCache.unlock(raceFish_free_room);
		RedisCache.set(raceFish_user_id_room_id + userId, rd.getRoomId());
		List<MatchFishBets> matchFishBets = matchFishBetsService.getUserMatchFishBets(rd.getOpenPrezeId(), userId);
		// 发送用户下注情况
		messageManager.sendMsg(userId, TypeCode.get_user_bet_record, matchFishBets);
		messageManager.sendMsg(userId, TypeCode.add_room, rd);
		sendMsg(rd, TypeCode.order_user_add, rd.getOnlineUsers());
	}

	public RoomDto getRoomById(String roomId) {
		return RedisCache.get(raceFish_room + roomId, RoomDto.class);
	}

	public void sendMsg(RoomDto room, String code, Object data) {
		List<String> userList = room.getUserLists();
		for (String userId : userList) {
			if (StringUtils.isBlank(userId)) {
				continue;
			}
			messageManager.sendMsg(userId, code, data);
		}
	}

	public void lock(String userId) {
		String id = RedisCache.get(raceFish_user_id_room_id + userId);
		RedisCache.lock(raceFish_room + id);
	}

	public void unlock(String userId) {
		String id = RedisCache.get(raceFish_user_id_room_id + userId);
		RedisCache.unlock(raceFish_room + id);
	}

	/**
	 * 获取用户信息
	 * 
	 * @param userId
	 */
	public void getUser(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		User user = userService.getUserById(userId);
		if (user == null) {
			throw ParamException.param_not_exception;
		}
		messageManager.sendMsg(userId, TypeCode.get_user, user);
	}

	public void findMatchFishOpenPrize(Integer page, Integer size, String userId) {
		String roomId = RedisCache.get(raceFish_user_id_room_id + userId);
		RoomDto roomDto = getRoomById(roomId);
		List<MatchFishOpenPrize> list = matchFishOpenPrizeService.findMatchFishOpenPrize(page, size,
				roomDto.getRoomId(), new Date(roomDto.getCreateTime()));
		messageManager.sendMsg(userId, TypeCode.get_room_match_fish_open_prize, list);

	}

	public void getLotteryEndDate(String userId) {
		String roomId = RedisCache.get(raceFish_user_id_room_id + userId);
		RoomDto rd = RedisCache.get(raceFish_room + roomId, RoomDto.class);
		messageManager.sendMsg(userId, TypeCode.get_lottery_end_date, rd.getCountdown());
	}

	public void getUserGold(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		User user = userService.getUserById(userId);
		if (user == null) {
			throw ParamException.param_not_exception;
		}
		messageManager.sendMsg(userId, TypeCode.get_user_gold, user.getGold());
	}

}
