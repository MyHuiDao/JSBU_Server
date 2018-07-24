package com.huidao.game.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.GameGoldExchange;
import com.huidao.common.entity.User;
import com.huidao.common.interfaces.game.IGameGoldExchangeService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.game.manager.UserManager;

/**
 * 金币相关
 * @author lenovo
 *
 */
@RequestMapping("/gold")
@Controller
public class GoldController {
	
	@Reference
	private IGameGoldExchangeService gameGoldExchangeService;
	
	@Reference
	private IUserService userService;
	@RequestMapping(value="/getExChangeGoldList",method = RequestMethod.GET)
	@JSON
	@Permission
	public MsgDto<List<GameGoldExchange>>  getExChangeGoldList(Integer type) {
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		return gameGoldExchangeService.getExChangeGoldList(user.getId(),type);
	}
}
