package com.huidao.admin.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.GameEmail;
import com.huidao.common.interfaces.admin.IGameEmailService;
import com.huidao.common.interfaces.game.ISendNoticeService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/gameEmail")
public class GameEmailController {

	@Reference
	private IGameEmailService gameEmailService;

	@Reference
	private ISendNoticeService sendNoticeService;

	@RequestMapping("/findGameEmailAll")
	@Permission("game_email_get")
	@JSON
	public MsgDto<Page<GameEmail>> findGameEmailAll(String title, String status) {

		return gameEmailService.findGameEmailAll(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(), title,
				status);
	}

	@RequestMapping("/addGameEmail")
	@Permission("game_email_add")
	@JSON
	public MsgDto<Integer> addGameEmail(GameEmail gameEmail) {
		gameEmailService.add(gameEmail);
		sendNoticeService.sendEmailNotice();
		return MsgFactory.success();
	}

	@RequestMapping("/updateGameEmail")
	@Permission("game_email_update")
	@JSON
	public MsgDto<Integer> updateGameEmail(GameEmail gameEmail) {

		return gameEmailService.update(gameEmail);
	}

	@RequestMapping("/deleteGameEmail")
	@Permission("game_email_delete")
	@JSON
	public MsgDto<Integer> deleteGameEmail(String id) {

		return gameEmailService.delete(id);
	}

	@RequestMapping("/getGameEmail")
	@Permission("game_email_get")
	@JSON
	public MsgDto<GameEmail> getGameEmail(String id) {

		return gameEmailService.get(id);
	}

	@RequestMapping("/sendEmail")
	@JSON
	public MsgDto<Integer> sendEmail(String emailId, String userIds) {
		String[] userId = userIds.split(",");
		return gameEmailService.sendEmail(emailId, userId);
	}

}
