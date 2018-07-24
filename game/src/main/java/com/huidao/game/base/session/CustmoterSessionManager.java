package com.huidao.game.base.session;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.entity.CustomerServiceMsg;
import com.huidao.common.entity.User;
import com.huidao.common.interfaces.game.ICustomerServiceMsgService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.game.base.message.dto.MsgDto;
import com.huidao.game.base.message.manager.MessageManager;
import com.huidao.game.contants.TypeCode;

@Component
public class CustmoterSessionManager {

	private static final Logger log = LoggerFactory.getLogger(CustmoterSessionManager.class);

	private static Map<String, Session> map = new HashMap<String, Session>();

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 游戏id 对应 系统id
	 */
	private static Map<String, String> gameUserSysUser = new HashMap<String, String>();
	private static Map<String, Integer> custmoterServiceCount = new HashMap<>();
	private static final String user_id = "user_id";

	/**
	 * 用户Map 缓存
	 */
	private static Map<String, User> userMap = new HashMap<>();

	@Autowired
	private MessageManager messageManager;

	@Reference
	private IUserService userService;

	@Reference
	private ICustomerServiceMsgService customerServiceMsgService;

	public static void closeAll() {
		for (Session session : map.values()) {
			try {
				session.close();
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	public void sendMsg(String userId, Session session, String msg) {
		CustomerServiceMsg c = new CustomerServiceMsg();
		Map<String, Object> result = new HashMap<>();
		Date d = new Date();
		result.put("time", sdf.format(d));
		c.setCreateTime(d);
		boolean isChating = false;
		for (Entry<String, String> entry : gameUserSysUser.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			if (userId.equals(entry.getKey())) {
				User user = userService.getUserById(userId);
				result.put("userId", user.getId());
				result.put("nickName", user.getNickName());
				result.put("code", user.getCode());
				result.put("headImg", user.getHeadImg());
				result.put("msg", msg);
				c.setMsg(msg);
				c.setUserId(userId);
				c.setSysUserId("");
				c.setType(1);
				boolean result1 = messageManager.sendMsg(map.get(entry.getValue()), TypeCode.game_user_chat, result);
				if (result1) {
					c.setStatus(3);
				} else {
					c.setStatus(2);
				}
				customerServiceMsgService.save(c);
				messageManager.sendMsg(session, TypeCode.game_user_chat, result);
				isChating = true;
				return;
			}
		}
		if (!isChating) {
			String sysUserId = null;
			int count = -1;
			for (Entry<String, Integer> entry : custmoterServiceCount.entrySet()) {
				if (count == -1) {
					sysUserId = entry.getKey();
					count = entry.getValue();
				}
				if (entry.getValue() < count) {
					sysUserId = entry.getKey();
					count = entry.getValue();
				}
			}
			c.setMsg(msg);
			c.setUserId(userId);
			c.setSysUserId("");
			c.setType(1);
			if (sysUserId == null) {
				log.info("没有找到客服");
				c.setStatus(2);
				customerServiceMsgService.save(c);
				messageManager.sendMsg(session, TypeCode.game_no_custmoter, result);
				return;
			}
			custmoterServiceCount.put(sysUserId, custmoterServiceCount.get(sysUserId) + 1);
			gameUserSysUser.put(userId, sysUserId);
			User user = userService.getUserById(userId);
			result.put("userId", user.getId());
			result.put("nickName", user.getNickName());
			result.put("code", user.getCode());
			result.put("headImg", user.getHeadImg());
			result.put("msg", msg);
			boolean result1 = messageManager.sendMsg(map.get(sysUserId), TypeCode.game_user_chat, result);
			if (result1) {
				c.setStatus(3);
			} else {
				c.setStatus(2);
			}
			customerServiceMsgService.save(c);
			messageManager.sendMsg(session, TypeCode.game_user_chat, result);
		}

	}

	public void sendMsg(CustomerServiceMsg csm) {
		String userId = csm.getUserId();
		Map<String, Object> result = new HashMap<>();
		Date d = new Date();
		result.put("time", sdf.format(d));
		boolean isChating = false;
		for (Entry<String, String> entry : gameUserSysUser.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			if (userId.equals(entry.getKey())) {
				User user = userMap.get(userId);
				if (user == null) {
					user = userService.getUserById(userId);
					userMap.put(userId, user);
				}
				result.put("userId", user.getId());
				result.put("nickName", user.getNickName());
				result.put("headImg", user.getHeadImg());
				result.put("code", user.getCode());
				result.put("msg", csm.getMsg());
				boolean result1 = messageManager.sendMsg(map.get(entry.getValue()), TypeCode.game_user_chat, result);
				isChating = true;
				if (result1) {
					customerServiceMsgService.update(csm.getId(), 3);
				}
				return;
			}
		}
		if (!isChating) {
			String sysUserId = null;
			int count = -1;
			for (Entry<String, Integer> entry : custmoterServiceCount.entrySet()) {
				if (count == -1) {
					sysUserId = entry.getKey();
					count = entry.getValue();
				}
				if (entry.getValue() < count) {
					sysUserId = entry.getKey();
					count = entry.getValue();
				}
			}
			if (sysUserId == null) {
				log.info("没有找到客服");
				return;
			}
			custmoterServiceCount.put(sysUserId, custmoterServiceCount.get(sysUserId) + 1);
			gameUserSysUser.put(userId, sysUserId);
			User user = userMap.get(userId);
			if (user == null) {
				user = userService.getUserById(userId);
				userMap.put(userId, user);
			}
			result.put("userId", user.getId());
			result.put("nickName", user.getNickName());
			result.put("code", user.getCode());
			result.put("headImg", user.getHeadImg());
			result.put("msg", csm.getMsg());
			boolean result1 = messageManager.sendMsg(map.get(sysUserId), TypeCode.game_user_chat, result);
			if (result1) {
				customerServiceMsgService.update(csm.getId(), 3);
			}
		}

	}

	/**
	 * 添加session管理
	 * 
	 * @param uid
	 * @param session
	 */
	public void addSession(String uid, Session session) {
		session.getUserProperties().put(user_id, uid);
		custmoterServiceCount.put(uid, 0);
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
		for (Entry<String, String> entry : gameUserSysUser.entrySet()) {
			if (uid.equals(entry.getValue())) {
				gameUserSysUser.remove(entry.getKey());
			}
		}
		custmoterServiceCount.remove(uid);
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
