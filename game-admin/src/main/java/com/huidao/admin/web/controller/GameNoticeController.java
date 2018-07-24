package com.huidao.admin.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.GameNotice;
import com.huidao.common.interfaces.game.notice.IGameNoticeService;
import com.huidao.common.msg.MsgDto;

@Controller
@RequestMapping("/gameNotice")
public class GameNoticeController {

	@Reference
	private IGameNoticeService gameNoticeService;

	@RequestMapping("/findGameNoticeAll")
	@Permission("game_notice_get")
	@JSON
	public MsgDto<List<GameNotice>> findGameNoticeAll(String type) {
		return gameNoticeService.findGameNoticeAll(type);
	}

	@RequestMapping("/addGameNotice")
	@Permission("game_notice_add")
	@JSON
	public MsgDto<Integer> addGameNotice(GameNotice gameNotice) {

		return gameNoticeService.add(gameNotice);
	}

	@RequestMapping("/updateGameNotice")
	@Permission("game_notice_update")
	@JSON
	public MsgDto<Integer> updateGameNotice(GameNotice gameNotice) {

		return gameNoticeService.update(gameNotice);
	}

	@RequestMapping("/deleteGameNotice")
	@Permission("game_notice_delete")
	@JSON
	public MsgDto<Integer> deleteGameNotice(String id) {

		return gameNoticeService.delete(id);
	}

	@RequestMapping("/getGameNotice")
	@Permission("game_notice_get")
	@JSON
	public MsgDto<GameNotice> getGameNotice(String id) {

		return gameNoticeService.get(id);
	}

}
