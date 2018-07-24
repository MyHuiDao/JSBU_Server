package com.huidao.game.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.dto.RankDto;
import com.huidao.common.interfaces.game.IRankService;
import com.huidao.common.msg.MsgDto;

@Controller
@RequestMapping("/rank")
public class GameRankController {

	@Reference
	private IRankService rankService;

	@RequestMapping(value = "/findRankActiveByDate")
	@JSON
	@Permission
	public MsgDto<List<RankDto>> findRankActiveByDate(Integer dateType) {
		return rankService.findRankActiveByDate(dateType);
	}

	@RequestMapping(value = "/findRankPayByDate")
	@JSON
	@Permission
	public MsgDto<List<RankDto>> findRankPayByDate(Integer dateType) {
		return rankService.findRankPayByDate(dateType);
	}
}
