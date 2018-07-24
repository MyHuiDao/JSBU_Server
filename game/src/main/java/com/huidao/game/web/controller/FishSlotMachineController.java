package com.huidao.game.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.FishMagnificationDeploy;
import com.huidao.common.entity.User;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.fish.slot.machine.IFishMagnificationDeployService;
import com.huidao.common.interfaces.fish.slot.machine.IFishSlotMachineBetService;
import com.huidao.common.interfaces.fish.slot.machine.IFishSlotMachineService;
import com.huidao.common.interfaces.fish.slot.machine.IFishSlotmachineDeployService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.game.manager.UserManager;

@Controller
@RequestMapping("/fsm")
public class FishSlotMachineController {

	@Reference
	private IFishSlotMachineService fishSlotMachineService;

	@Reference
	private IUserService userService;

	@Reference
	private IFishSlotMachineBetService fishSlotMachineBetService;

	@Reference
	private IFishSlotmachineDeployService fishSlotmachineDeployService;

	@Reference
	private IFishMagnificationDeployService fishMagnificationDeployService;

	/**
	 * 获取鱼的所有倍数
	 * 
	 * @return
	 */
	@RequestMapping("/getFishSlotmachineDeploy")
	@Permission
	@JSON
	public MsgDto<List<FishMagnificationDeploy>> getFishSlotmachineDeploy() {

		return fishMagnificationDeployService.findAll();
	}

	/**
	 * 获取开奖结果
	 * 
	 * @param bet
	 * @return
	 */
	@RequestMapping("/getLotteryResult")
	@Permission
	@JSON
	public MsgDto<Map<String, Object>> getLotteryResult(String bet) {
		if (StringUtils.isBlank(bet)) {
			throw ParamException.param_not_exception;
		}
		// 得到下注用户信息
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		MsgDto<Map<String, Object>> fishBet = fishSlotMachineBetService.bet(user, bet);
		return fishBet;
	}
}
