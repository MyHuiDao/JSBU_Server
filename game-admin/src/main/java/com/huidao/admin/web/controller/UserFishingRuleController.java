package com.huidao.admin.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.UserFishingRule;
import com.huidao.common.interfaces.admin.IFishingRuleService;
import com.huidao.common.interfaces.admin.IUserFishingRuleService;
import com.huidao.common.msg.MsgDto;

@Controller
@RequestMapping("/userFishingRule")
public class UserFishingRuleController {

	@Reference
	private IUserFishingRuleService userFishingRuleService;
	@Reference
	private IFishingRuleService fishingRuleService;

	/**
	 * 获取用户捕鱼规则
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/getUserFishingRuleData")
	@Permission("user_fishing_rule_get")
	@JSON
	public MsgDto<List<UserFishingRule>> getUserFishingRuleData(String id) {

		return userFishingRuleService.getUserFishingRuleData(id);
	}

	/**
	 * 为用户添加捕鱼规则
	 * 
	 * @param userFishingRule
	 * @return
	 */
	@RequestMapping(value = "/addUserFishingRule", method = RequestMethod.POST)
	@Permission("user_fishing_rule_add")
	@JSON
	public MsgDto<String> addUserFishingRule(UserFishingRule userFishingRule) {
		return userFishingRuleService.add(userFishingRule);
	}

	/**
	 * 删除用户捕鱼规则
	 * 
	 * @param userFishingRule
	 * @return
	 */
	@RequestMapping(value = "/deleteUserFishingRule", method = RequestMethod.GET)
	@Permission("user_fishing_rule_delete")
	@JSON
	public MsgDto<String> deleteUserFishingRule(String id) {

		return userFishingRuleService.delete(id);
	}

	/**
	 * 修改用户捕鱼规则
	 * 
	 * @param userFishingRule
	 * @return
	 */
	@RequestMapping("/updateUserFishingRule")
	@Permission("user_fishing_rule_disabled")
	@JSON
	public MsgDto<String> updateUserFishingRule(String id, Integer status) {

		return userFishingRuleService.update(id, status);
	}

}
