package com.huidao.admin.web.timer;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.interfaces.admin.IAgenApplyService;
import com.huidao.common.interfaces.admin.ISysUserService;
import com.huidao.common.interfaces.game.IUserService;

@Component
public class AgenTimer {
	@PostConstruct
	public void init() {
		new Thread(new AgetApplicationImpl(agenApplyService, userService, sysUserService)).start();
	}
	@Reference
	private IAgenApplyService agenApplyService;
	@Reference
	private IUserService userService;
	@Reference
	private ISysUserService sysUserService;
}
