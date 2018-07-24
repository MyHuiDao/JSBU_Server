package com.huidao.admin.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.GameFire;
import com.huidao.common.interfaces.game.IGameFireService;
import com.huidao.common.msg.MsgDto;

@Controller
@RequestMapping("/gameFire")
public class GameFireController {

	@Reference
	private IGameFireService gameFireService;

	@RequestMapping("/findGameFireAll")
	@Permission("game_fire_get")
	@JSON
	public MsgDto<List<GameFire>> findGameFireAll() {

		return gameFireService.findAll();
	}

	@RequestMapping("/refreshCache")
	@Permission("game_fire_refresh")
	@JSON
	public MsgDto<String> refreshCache() {

		return gameFireService.refreshCache();
	}

	@RequestMapping("/updateGameFire")
	@Permission("game_fire_update")
	@JSON
	public MsgDto<Integer> updateGameFire(GameFire gameFire) {

		return gameFireService.updateGameFire(gameFire);
	}

}
