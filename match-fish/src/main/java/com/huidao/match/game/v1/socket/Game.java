package com.huidao.match.game.v1.socket;

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
import com.huidao.common.entity.User;
import com.huidao.common.enums.GameType;
import com.huidao.common.interfaces.game.IGameUserOnlineService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.match.base.anno.LockRoom;
import com.huidao.match.base.anno.UnLockRoom;
import com.huidao.match.base.message.manager.MessageManager;
import com.huidao.match.base.session.SessionManager;
import com.huidao.match.base.spring.JSONDto;
import com.huidao.match.game.manager.RoomManager;
import com.yehebl.handler.HandlerUtil;

@ServerEndpoint(value = "/v1/game/{token}", configurator = SpringConfigurator.class)
public class Game {

	private static final Logger log = LoggerFactory.getLogger(Game.class);

	@Autowired
	private SessionManager sessionManager;

	@Reference
	private IUserService userService;

	@Reference
	private IGameUserOnlineService gameUserOnlineService;

	@Autowired
	private RoomManager roomManager;

	@OnOpen
	public void onOpen(Session session, @PathParam(value = "token") String token) {
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
		/* session.setMaxIdleTimeout(10 * 1000); */
		sessionManager.addSession(user.getId(), token, session);
		gameUserOnlineService.startGame(GameType.match_fish, user.getId());

	}

	@OnMessage
	public void onMessage(Session session, String msg, @PathParam(value = "token") String token) {
		userService.refreshTimeOut(token); // 刷新token过期时间
		log.info("接受消息：" + msg);
		String code = null;
		String userId = sessionManager.getUserId(session);
		try {
			long startTime = new Date().getTime();
			if (StringUtils.isBlank(msg)) {
				log.error("msg不能为空");
				return;
			}
			JSONObject json = JSONObject.parseObject(msg);
			code = json.getString("code");
			if (StringUtils.isBlank(code)) {
				log.error("code不能为空");
				return;
			}
			Map<Object, Object> map = new HashMap<Object, Object>();
			if (HandlerUtil.isExistType(code, Session.class)) {
				map.put(Session.class, session);
			}
			if (HandlerUtil.getMethod(code).getAnnotation(LockRoom.class) != null) {
				roomManager.lock(userId);
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
			log.info(e.getMessage(), e);
		} finally {
			if (HandlerUtil.getMethod(code).getAnnotation(UnLockRoom.class) != null) {
				roomManager.unlock(userId);
			}
		}
	}

	@OnMessage
	public void OnPong(Session session, byte[] b) {
		if (session == null || !session.isOpen()) {
			return;
		}
		session.getAsyncRemote().sendObject(b);
	}

	@OnClose
	public void onClose(Session session) {
		String userId = sessionManager.getUserId(session);
		sessionManager.removeSession(session);
		if (StringUtils.isBlank(userId)) {
			log.info("用户未登录断开连接");
			return;
		}
		roomManager.exitRoom(userId);
		gameUserOnlineService.endGame(GameType.match_fish, userId);
		log.info(userId + "离开了");
	}

	@OnError
	public void OnError(Session session, Throwable t) {
		log.error("webSocket ERROR:" + t.getMessage());
	}
}
