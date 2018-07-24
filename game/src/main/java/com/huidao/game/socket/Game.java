package com.huidao.game.socket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.huidao.common.entity.CustomerServiceMsg;
import com.huidao.common.entity.SysUser;
import com.huidao.common.entity.User;
import com.huidao.common.interfaces.admin.ISysUserService;
import com.huidao.common.interfaces.game.ICustomerServiceMsgService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.huidao.game.base.message.manager.MessageManager;
import com.huidao.game.base.session.CustmoterSessionManager;
import com.huidao.game.base.session.SessionManager;
import com.huidao.game.base.spring.JSONDto;
import com.huidao.game.contants.TypeCode;
import com.huidao.game.service.CustmoterServiceThread;
import com.yehebl.handler.HandlerUtil;

@ServerEndpoint(value = "/game/hall/{type}/{token}", configurator = SpringConfigurator.class)
public class Game {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final Logger log = LoggerFactory.getLogger(Game.class);

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private CustmoterSessionManager custmoterSessionManager;

	@Reference
	private IUserService userService;

	@Reference
	private ISysUserService sysUserService;

	@Reference
	private ICustomerServiceMsgService customerServiceMsgService;

	@Autowired
	private MessageManager messageManager;

	@OnOpen
	public void onOpen(Session session, @PathParam(value = "type") Integer type,
			@PathParam(value = "token") String token) {
		session.setMaxTextMessageBufferSize(99999999);
		log.info("连接进入" + token);
		if (2 == type) {
			MsgDto<SysUser> sysUserMsg = sysUserService.getSysUserByToken(token);
			if (MsgFactory.isSuccess(sysUserMsg)) {
				custmoterSessionManager.addSession(sysUserMsg.getData().getId(), session);
				new CustmoterServiceThread(customerServiceMsgService, custmoterSessionManager).start();
				return;
			} else {
				try {
					session.close();
				} catch (IOException e1) {
					log.error(e1.getMessage(), e1);
				}
			}
			return;
		}
		try {
			User user = userService.getUserByToken(token);
			sessionManager.addSession(user.getId(), token, session);
			List<CustomerServiceMsg> data = customerServiceMsgService.userFindNoSendMsg(user.getId()).getData();
			Map<String, SysUser> sysUserMap = new HashMap<>();
			for (int i = 0; i < data.size(); i++) {
				Map<String, Object> result = new HashMap<>();
				SysUser sysUser = sysUserMap.get(data.get(i).getSysUserId());
				if (sysUser == null) {
					sysUser = sysUserService.get(data.get(i).getSysUserId()).getData();
					sysUserMap.put(sysUser.getId(), sysUser);
				}
				Date d = new Date();
				result.put("time", sdf.format(d));
				result.put("userId", sysUser.getId());
				result.put("nickName", sysUser.getNickName());
				result.put("msg", data.get(i).getMsg());
				messageManager.sendMsg(session, TypeCode.game_user_chat_reply, result);
			}
		} catch (Exception e) {
			try {
				session.close();
			} catch (IOException e1) {
				log.error(e1.getMessage(), e1);
			}
		}

	}

	@OnMessage
	public void onMessage(Session session, String msg, @PathParam(value = "type") Integer type,
			@PathParam(value = "token") String token) {
		if (2 == type) {
			sysUserService.refreshSysToken(token);
		} else {
			userService.refreshTimeOut(token); // 刷新token过期时间
		}
		log.info("接受消息：" + msg);
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
			JSON json1= HandlerUtil.getMethod(code).getAnnotation(JSON.class);
			if(json1!=null) {
				JSONDto jd= new JSONDto();
				jd.setClazz(json1.clazz());
				jd.setProperty(json1.property());
				jd.setFilterProperty(json1.filterProperty());
				MessageManager.currentJson.set(jd);
			}
			HandlerUtil.handler(code, map);
			log.debug("执行一个请求：" + (new Date().getTime() - startTime) + "毫秒");
		} catch (Exception e) {
			log.info(e.getMessage(), e);
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
	public void onClose(Session session, @PathParam(value = "type") Integer type) {
		if (2 == type) {
			custmoterSessionManager.removeSession(session);
			return;
		}
		String userId = sessionManager.getUserId(session);
		sessionManager.removeSession(session);
		if (StringUtils.isBlank(userId)) {
			log.info("用户未登录断开连接");
			return;
		}
		log.info(userId + "离开了");
	}

	@OnError
	public void OnError(Session session, Throwable t) {
		log.error("webSocket ERROR:" + t.getMessage());
	}
}
