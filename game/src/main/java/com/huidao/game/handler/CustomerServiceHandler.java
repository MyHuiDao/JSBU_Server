package com.huidao.game.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.entity.CustomerServiceMsg;
import com.huidao.common.entity.SysUser;
import com.huidao.common.interfaces.admin.ISysUserService;
import com.huidao.common.interfaces.game.ICustomerServiceMsgService;
import com.huidao.game.base.message.manager.MessageManager;
import com.huidao.game.base.session.CustmoterSessionManager;
import com.huidao.game.base.session.SessionManager;
import com.huidao.game.contants.TypeCode;
import com.yehebl.handler.annotaction.Body;
import com.yehebl.handler.annotaction.Handler;

@Component
@Handler
public class CustomerServiceHandler {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private CustmoterSessionManager custmoterSessionManager;

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private MessageManager messageManager;

	@Reference
	private ISysUserService sysUserService;

	@Reference
	private ICustomerServiceMsgService customerServiceMsgService;

	@Handler(TypeCode.game_user_chat)
	public void sendMsg(Session session, @Body String data) {
		String userId = sessionManager.getUserId(session);
		custmoterSessionManager.sendMsg(userId, session, data);
	}

	@Handler(TypeCode.game_user_chat_reply)
	public void replyMsg(Session session, String userId, String data) {
		String sysUserid = custmoterSessionManager.getUserId(session);
		SysUser sysUser = sysUserService.get(sysUserid).getData();
		CustomerServiceMsg c = new CustomerServiceMsg();
		Date d = new Date();
		c.setCreateTime(d);
		Map<String, Object> result = new HashMap<>();
		result.put("time", sdf.format(d));
		result.put("userId", userId);
		result.put("nickName", sysUser.getNickName());
		result.put("msg", data);
		c.setMsg(data);
		c.setType(0);
		c.setSysUserId(sysUser.getId());
		c.setUserId("");
		c.setStatus(2);
		customerServiceMsgService.save(c);
		try {
			boolean result1 = messageManager.sendMsg(userId, TypeCode.game_user_chat_reply, result);
			if (result1) {
				customerServiceMsgService.update(c.getId(), 3);
			}
		} catch (Exception e) {
			
		}
		messageManager.sendMsg(session, TypeCode.game_user_chat_reply, result);
	}

}
