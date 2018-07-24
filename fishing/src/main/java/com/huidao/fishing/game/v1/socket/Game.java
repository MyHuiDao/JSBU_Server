package com.huidao.fishing.game.v1.socket;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.huidao.common.anno.JSON;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.entity.User;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.game.IGameUserOnlineService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.fishing.base.message.manager.MessageManager;
import com.huidao.fishing.base.session.SessionManager;
import com.huidao.fishing.base.spring.JSONDto;
import com.huidao.fishing.game.dto.RoomDto;
import com.huidao.fishing.game.manager.RoomManager;
import com.yehebl.handler.HandlerUtil;

@ServerEndpoint(value = "/v1/game/{token}", configurator = SpringConfigurator.class)
public class Game {

	private static final Logger log = LoggerFactory.getLogger(Game.class);

	@Autowired
	private SessionManager sessionManager;

	@Reference
	private IUserService userService;

	@Autowired
	private RoomManager roomManager;

	@Reference
	private IGameUserOnlineService gameUserOnlineService;

	@Reference
	private IGameSettingService gameSettingService;

	@OnOpen
	public void onOpen(Session session, @PathParam(value = "token") String token) {
		String val = gameSettingService.getValue(GameSettingContants.switch_game);
		if (val.equals("N")) {
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		session.setMaxTextMessageBufferSize(99999999);
		log.info("连接进入" + token);
		User user = userService.getUserByToken(token);
		if (user == null) {
			try {
				session.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
			return;
		}

		session.setMaxIdleTimeout(10 * 1000);
		sessionManager.addSession(user.getId(), token, session);
	}

	@OnMessage
	public void onMessage(Session session, String msg, @PathParam(value = "token") String token) {
		userService.refreshTimeOut(token); // 刷新token过期时间
		// log.info("接受消息："+msg);
		try {
			long startTime = new Date().getTime();
			if (StringUtils.isBlank(msg)) {
				log.error("msg不能为空");
				return;
			}
			JSONObject json = JSONObject.parseObject(msg);
			String code = json.getString("code");
			if (StringUtils.isBlank(code)) {
				log.error("code不能为空");
				return;
			}
			Map<Object, Object> map = new HashMap<Object, Object>();
			if (HandlerUtil.isExistType(code, Session.class)) {
				map.put(Session.class, session);
			}
			HandlerUtil.setData(code, map, json.getString("data"));
			JSON json1 = HandlerUtil.getMethod(code).getAnnotation(JSON.class);
			if (json1 != null) {
				JSONDto jd = new JSONDto();
				jd.setClazz(json1.clazz());
				jd.setProperty(json1.property());
				jd.setFilterProperty(json1.filterProperty());
				MessageManager.currentJson.set(jd);
			}
			HandlerUtil.handler(code, map);
			log.debug("执行一个请求：" + (new Date().getTime() - startTime) + "毫秒");
		} catch (Exception e) {
			log.error("消息：" + msg);
			log.error(e.getMessage(), e);
		} finally {
			String userId = sessionManager.getUserId(session);
			String roomId = roomManager.getRoomId(userId);
			if (StringUtils.isNotBlank(roomId)) {
				RoomDto room = RoomManager.getRoom(roomId);
				RoomManager.unLockRoom(roomId);
				RoomManager.unLockFree(room.getAreaId());
			}

		}
	}

	@OnMessage
	public void OnPong(Session session, byte[] b) {
		if (session == null || !session.isOpen()) {
			return;
		}
		String userId = sessionManager.getUserId(session);
		session.getAsyncRemote().sendObject(b);
		if (StringUtils.isNotBlank(userId)) {
			Object object = session.getUserProperties().get("user_last_request_time");
			if (object != null && System.currentTimeMillis() - Long.valueOf(object.toString()) < 20 * 1000) {
				return;
			}
			session.getUserProperties().put("user_last_request_time", System.currentTimeMillis());
			RedisCache.set(CacheContants.user_last_request_time + userId, System.currentTimeMillis(), 30);
		}

	}

	@OnClose
	public void onClose(Session session) {
		String userId = sessionManager.getUserId(session);
		sessionManager.removeSession(session);
		if (StringUtils.isBlank(userId)) {
			log.error("用户未登录断开连接");
			return;
		}
		String roomId = roomManager.getRoomId(userId);
		if (StringUtils.isNotBlank(roomId)) {
			RoomDto room = null;
			try {
				room = RoomManager.getRoom(roomId);
				roomManager.exitRoom(userId, roomId, true);
			} finally {
				RoomManager.unLockRoom(roomId);
				RoomManager.unLockFree(room.getAreaId());
			}
			log.error("解散房间:{}", roomId);
		}
	}

	@OnError
	public void OnError(Session session, Throwable t) {
		log.error("webSocket ERROR:" + t.getMessage());
		String userId = sessionManager.getUserId(session);
		sessionManager.removeSession(session);
		if (StringUtils.isBlank(userId)) {
			log.error("用户未登录断开连接");
			return;
		}
		String roomId = roomManager.getRoomId(userId);
		if (StringUtils.isNotBlank(roomId)) {
			RoomDto room = null;
			try {
				room = RoomManager.getRoom(roomId);
				roomManager.exitRoom(userId, roomId, true);
			} finally {
				RoomManager.unLockRoom(roomId);
				RoomManager.unLockFree(room.getAreaId());
			}
			log.error("解散房间:{}", roomId);
		}
	}
}
