package com.huidao.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.interfaces.game.IKickOutUserService;
import com.huidao.common.msg.MsgDto;

@Controller
@RequestMapping("/kuc")
public class KickOutUserController {

	@Reference
	private IKickOutUserService kickOutUserService;

	@RequestMapping("/kickOutUser")
	@Permission("kick_out_user")
	@JSON
	public MsgDto<String> kickOutUser(String userId) {

		return kickOutUserService.kickOutUser(userId);
	}

	@RequestMapping("/kickOutUserAll")
	@Permission("kick_out_user_all")
	@JSON
	public MsgDto<String> kickOutUserAll() {

		return kickOutUserService.kickOutUserAll();
	}

}
