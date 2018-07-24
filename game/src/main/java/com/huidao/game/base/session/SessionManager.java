package com.huidao.game.base.session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huidao.game.base.message.dto.MsgDto;
import com.huidao.game.base.message.manager.MessageManager;

@Component
public class SessionManager {

	private static final Logger log = LoggerFactory.getLogger(SessionManager.class);

	private static Map<String, Session> map = new HashMap<String, Session>();
	private static final String user_id = "user_id";
	private static final String token = "token";

	@Autowired
	private MessageManager messageManager;

	public static void closeAll() {
		for (Session session : map.values()) {
			try {
				session.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 添加session管理
	 * 
	 * @param uid
	 * @param session
	 */
	public void addSession(String uid, String tokenStr, Session session) {
		session.getUserProperties().put(user_id, uid);
		session.getUserProperties().put(token, tokenStr);
		map.put(uid, session);
	}

	/**
	 * 获取用户id
	 * 
	 * @param session
	 * @return
	 */
	public String getUserId(Session session) {
		return (String) session.getUserProperties().get(user_id);
	}

	/**
	 * 移除session
	 * 
	 * @param session
	 */
	public void removeSession(Session session) {
		String uid = (String) session.getUserProperties().get(user_id);
		map.remove(uid);
	}

	/**
	 * 获取连接session
	 * 
	 * @param userId
	 * @return
	 */
	public Session getSession(String userId) {
		return map.get(userId);
	}

	/**
	 * 所有用户发送消息
	 */
	public void sendAllUser(MsgDto md) {
		for (Session session : map.values()) {
			messageManager.sendMsg(session, md);
		}
	}

	public void offline(String userId) {
		Session session = map.get(userId);
		if (session != null) {
			removeSession(session);
			try {
				session.close();
			} catch (IOException e) {
				throw new RuntimeException("关闭session异常：" + e.getMessage());
			}
		}
	}
}
