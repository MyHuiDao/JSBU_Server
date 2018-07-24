package com.huidao.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.interfaces.game.ISendNoticeService;
import com.huidao.game.base.message.manager.MessageManager;
import com.huidao.game.base.session.SessionManager;
import com.huidao.game.contants.TypeCode;

@Component
@Service
public class SendNoticeService implements ISendNoticeService {

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private MessageManager messageManager;

	@Override
	public void sendEmailNotice() {
		sessionManager.sendAllUser(new com.huidao.game.base.message.dto.MsgDto(TypeCode.game_notice_email_news, null));

	}

	@Override
	public void sendUserEmialNotice(String userId) {
		messageManager.sendMsg(userId, TypeCode.game_notice_email_news, null);
	}

	@Override
	public void noticeRechargeInfo(String userId, Integer gold) {
		messageManager.sendMsg(userId, TypeCode.game_notice_user_recharge_info, gold);

	}

}
