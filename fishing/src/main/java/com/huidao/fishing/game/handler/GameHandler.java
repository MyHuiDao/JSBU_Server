package com.huidao.fishing.game.handler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.huidao.common.anno.JSON;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.dto.GetOutGoldDto;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.fishing.base.message.manager.MessageManager;
import com.huidao.fishing.base.session.SessionManager;
import com.huidao.fishing.game.contants.TypeCode;
import com.huidao.fishing.game.dto.FishDto;
import com.huidao.fishing.game.dto.RoomDto;
import com.huidao.fishing.game.enums.RoomType;
import com.huidao.fishing.game.manager.IRoomUpdate;
import com.huidao.fishing.game.manager.RoomManager;
import com.yehebl.handler.annotaction.Body;
import com.yehebl.handler.annotaction.Handler;

/**
 * 游戏处理器
 * 
 * @author lenovo
 *
 */
@Handler
@Component
public class GameHandler {

	@Autowired
	private RoomManager roomManager;

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private MessageManager messageManager;

	@Reference
	private IGameSettingService gameSettingService;

	@Reference
	private IUserService userSerivce;

	@Handler(TypeCode.join_game_area)
	@JSON(clazz = { RoomDto.class, FishDto.class }, property = {
			"fireNum,userList,roomType,userId,userFireLevelMap,fishList,fireMap,userGoldMap",
			"id,fishType,runPoint,randomX,randomY,speed,level,group" })
	public void JoinGameArea(Session session, @Body String data) {
		roomManager.addRoom(sessionManager.getUserId(session), data);
	}

	@Handler(TypeCode.exit_room)
	public void exitGameArea(Session session) {
		String userId = sessionManager.getUserId(session);
		String roomId = roomManager.getRoomId(userId);
		if (StringUtils.isBlank(roomId)) {
			throw ParamException.param_exception;
		}
		roomManager.exitRoom(userId, roomId, true);
	}

	@Handler(TypeCode.open_fire)
	public void openFire(Session session, @Body String data) {
		Long time = (Long) session.getUserProperties().get("openFireTime");
		if (time != null && System.currentTimeMillis() - time <= 200) {
			return;
		}
		session.getUserProperties().put("openFireTime", System.currentTimeMillis());
		String userId = sessionManager.getUserId(session);
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		Double angle = Double.valueOf(data);
		roomManager.addFrie(userId, angle);
	}

	@JSON(clazz = { FishDto.class }, property = { "id,fishType,runPoint,randomX,randomY,speed,group,level" })
	@Handler(TypeCode.fish_delete)
	public void deleteFish(Session session, @Body String data) {
		roomManager.deleteFish(session, data);
	}

	@Handler(TypeCode.fire_delete)
	@JSON(clazz = { FishDto.class }, property = { "id,fishType,runPoint,randomX,randomY,speed,group,level" })
	public void deleteFire(Session session, @Body String data) {
		if (StringUtils.isBlank(data)) {
			throw ParamException.param_not_exception;
		}
		JSONObject jsonObject = JSONObject.parseObject(data);
		Double x = jsonObject.getDouble("x");
		Double y = jsonObject.getDouble("y");
		String id = jsonObject.getString("fireId");
		String fishIdList = jsonObject.getString("fishList");
		roomManager.deleteFire(session, x, y, id, fishIdList.split(","));
	}

	@Handler(TypeCode.fish_start)
	@JSON(clazz = { FishDto.class }, property = { "id,fishType,runPoint,randomX,randomY,speed,group,level" })
	public void fishStart(Session session, @Body String data) {
		String currentUserId = sessionManager.getUserId(session);
		String roomId = roomManager.getRoomId(currentUserId);
		if (StringUtils.isBlank(roomId)) {
			throw ParamException.param_exception;
		}
		RoomDto room = RoomManager.getRoom(roomId);
		if (room == null) {
			throw ParamException.param_exception;
		}
		Integer v = Integer.valueOf(gameSettingService.getValue(GameSettingContants.min_fish_group));
		if ((new Date().getTime() - room.getGroupStartDate().getTime()) / 1000 > v) {
			// 触发渔阵
			roomManager.addFishGroup(room);
			return;
		}
		roomManager.sendMsg(room, TypeCode.fish_start, data);
	}

	@Handler(TypeCode.forward_server_info)
	public void forwardServerInfo(Session session, @Body String data) {
		String currentUserId = sessionManager.getUserId(session);
		String roomId = roomManager.getRoomId(currentUserId);
		RoomDto room = RoomManager.getRoom(roomId);
		if (StringUtils.isBlank(roomId)) {
			throw ParamException.param_exception;
		}
		if (room == null) {
			throw ParamException.param_exception;
		}
		roomManager.sendMsgNoSelf(currentUserId, room, TypeCode.forward_server_info, JSONObject.parse(data));
	}

	@Handler(TypeCode.fire_level_add)
	public void fireLevelAdd(Session session) {
		String currentUserId = sessionManager.getUserId(session);
		String roomId = roomManager.getRoomId(currentUserId);
		RoomDto room = RoomManager.getRoom(roomId);
		if (StringUtils.isBlank(roomId)) {
			throw ParamException.param_exception;
		}
		if (room == null) {
			throw ParamException.param_exception;
		}
		roomManager.updateRoom(roomId, new IRoomUpdate() {
			@Override
			public void update(RoomDto room) {
				Integer level = room.getUserFireLevelMap().get(currentUserId);
				if (level >= 9) {
					messageManager.sendMsg(session, TypeCode.fire_level_add_max);
					return;
				} else {
					room.getUserFireLevelMap().put(currentUserId, level + 1);
					Map<String, Object> map = new HashMap<>();
					map.put("userId", currentUserId);
					map.put("level", level + 1);
					roomManager.sendMsg(room, TypeCode.fire_level_add, map);
				}
			}
		}, false, null);
	}

	@Handler(TypeCode.fire_level_reduce)
	public void fireLevelReduce(Session session) {
		String currentUserId = sessionManager.getUserId(session);
		String roomId = roomManager.getRoomId(currentUserId);
		RoomDto room = RoomManager.getRoom(roomId);
		if (StringUtils.isBlank(roomId)) {
			throw ParamException.param_exception;
		}
		if (room == null) {
			throw ParamException.param_exception;
		}
		roomManager.updateRoom(roomId, new IRoomUpdate() {
			@Override
			public void update(RoomDto room) {
				Integer level = room.getUserFireLevelMap().get(currentUserId);
				if (level <= 1) {
					roomManager.sendMsg(room, TypeCode.fire_level_reduce_min);
					return;
				} else {
					room.getUserFireLevelMap().put(currentUserId, level - 1);
					Map<String, Object> map = new HashMap<>();
					map.put("userId", currentUserId);
					map.put("level", level - 1);
					roomManager.sendMsg(room, TypeCode.fire_level_reduce, map);
				}
			}
		}, false, null);
	}

	@Handler(TypeCode.game_user_skill)
	public void forwardingSkillInfo(Session session, @Body String data) {
		String currentUserId = sessionManager.getUserId(session);
		String roomId = roomManager.getRoomId(currentUserId);
		RoomDto room = RoomManager.getRoom(roomId);
		if (StringUtils.isBlank(roomId)) {
			throw ParamException.param_exception;
		}
		if (room == null) {
			throw ParamException.param_exception;
		}
		JSONObject jsonObject = JSONObject.parseObject(data);
		if (jsonObject == null) {
			throw ParamException.param_exception;
		}
		if (jsonObject.get("skillName").equals("frozenFish")) {
			if (room.getRoomType().equals(RoomType.gold)) {
				Long gold = Long.valueOf(gameSettingService.getValue(GameSettingContants.launch_frozen_skills));
				roomManager.updateRoom(roomId, new IRoomUpdate() {
					@Override
					public void update(RoomDto room) {
						long outGold = room.getUserGoldMap().get(currentUserId) - gold;
						if (outGold >= 0) {
							GetOutGoldDto getOutGoldDto = room.getGetOutGoldMap().get(currentUserId);
							getOutGoldDto
									.setCurrentGold(getOutGoldDto.getCurrentGold().subtract(BigDecimal.valueOf(gold)));
							room.getUserGoldMap().put(currentUserId, outGold);
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("userId", currentUserId);
							map.put("gold", gold);
							roomManager.sendMsg(room, TypeCode.game_consumption_gold, map);
						} else {
							messageManager.sendMsg(session, TypeCode.gold_inadequate);
						}
					}
				}, false, null);

			}
		}
		roomManager.sendMsg(room, TypeCode.game_user_skill, jsonObject);
	}

	@Handler(TypeCode.game_user_end_skill)
	public void forwardingSkillEndInfo(Session session, @Body String data) {
		String currentUserId = sessionManager.getUserId(session);
		String roomId = roomManager.getRoomId(currentUserId);
		RoomDto room = RoomManager.getRoom(roomId);
		if (StringUtils.isBlank(roomId)) {
			throw ParamException.param_exception;
		}
		if (room == null) {
			throw ParamException.param_exception;
		}
		roomManager.sendMsg(room, TypeCode.game_user_end_skill, JSONObject.parse(data));
	}

	@Handler(TypeCode.game_fish_group_start)
	public void fishGroupStart(Session session, @Body String data) {
		String currentUserId = sessionManager.getUserId(session);
		String roomId = roomManager.getRoomId(currentUserId);
		if (StringUtils.isBlank(roomId)) {
			throw ParamException.param_exception;
		}
		RoomDto room = RoomManager.getRoom(roomId);
		if (room == null) {
			throw ParamException.param_exception;
		}
		roomManager.sendMsg(room, TypeCode.game_fish_group_start, data);
	}

	@Handler(TypeCode.fish_array_date)
	public void fishArrayPrompt(Session session) {
		String currentUserId = sessionManager.getUserId(session);
		String roomId = roomManager.getRoomId(currentUserId);
		RoomDto room = RoomManager.getRoom(roomId);
		roomManager.fishArrayPrompt(room);
	}

	@Handler(TypeCode.supplement_fish)
	public void supplementFish(Session session) {
		roomManager.supplementFish(session);
	}

}
