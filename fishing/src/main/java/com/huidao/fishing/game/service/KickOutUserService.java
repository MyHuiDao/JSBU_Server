package com.huidao.fishing.game.service;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.interfaces.game.IKickOutUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.huidao.fishing.base.session.SessionManager;

@Service
@Component
public class KickOutUserService implements IKickOutUserService {

	@Override
	public MsgDto<String> kickOutUser(String userId) {
		SessionManager.offline(userId);
		return MsgFactory.success();
	}

	@Override
	public MsgDto<String> kickOutUserAll() {
		SessionManager.closeAll();
		return MsgFactory.success();
	}

}
