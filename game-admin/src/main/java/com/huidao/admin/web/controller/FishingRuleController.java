package com.huidao.admin.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.entity.FishingRule;
import com.huidao.common.entity.UserFishingRule;
import com.huidao.common.interfaces.admin.IFishingRuleService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;

@Controller
@RequestMapping("/fishingRule")
public class FishingRuleController {

	@Reference
	private IFishingRuleService fishingRuleService;

	/**
	 * 分页显示捕鱼规则
	 * 
	 * @return
	 */
	@RequestMapping("/findFishingRuleAll")
	@Permission("game_setting_fishing_rule")
	@JSON
	public MsgDto<List<FishingRule>> findFishingRuleAll(Integer type) {
		return MsgFactory.success(fishingRuleService.findPageFishingRule(type));
	}

	/**
	 * 添加捕鱼规则
	 * 
	 * @param fishingRule
	 * @return
	 */
	@RequestMapping("/addFishingRule")
	@Permission("fishing_rule_new")
	@JSON
	public MsgDto<String> addFishingRule(FishingRule fishingRule) {
		if (fishingRule.getWinNum() > 100) {
			return MsgFactory.failMsg("赢的概率不能大于100");
		}
		return fishingRuleService.add(fishingRule);
	}

	/**
	 * 修改捕鱼规则
	 * 
	 * @param fishingRule
	 * @return
	 */
	@RequestMapping("/updateFishingRule")
	@Permission("fishing_rule_update")
	@JSON
	public MsgDto<String> updateFishingRule(FishingRule fishingRule) {
		if (fishingRule.getWinNum() > 100) {
			return MsgFactory.failMsg("赢的概率不能大于100");
		}
		return fishingRuleService.update(fishingRule);
	}

	/**
	 * 删除捕鱼规则
	 * 
	 * @param fishingRule
	 * @return
	 */
	@RequestMapping("/deleteFishingRule")
	@Permission("fishing_rule_delete")
	@JSON
	public MsgDto<String> deleteFishingRule(String id) {

		return fishingRuleService.delete(id);
	}

	/**
	 * 获取捕鱼规则
	 * 
	 * @param fishingRule
	 * @return
	 */
	@RequestMapping(value = "/getFishingRule", method = RequestMethod.GET)
	@Permission("fishing_rule_get")
	@JSON
	public MsgDto<FishingRule> getFishingRule(String id) {

		return fishingRuleService.getFishingRule(id);
	}

	/**
	 * 清除公共规则缓存
	 * 
	 * @return
	 */
	@RequestMapping(value = "/removePublicCaching", method = RequestMethod.GET)
	@Permission("fishing_rule_remove_public")
	@JSON
	public MsgDto<String> removePublicCaching() {
		RedisCache.remove(CacheContants.fishing_rule_public_list+"gold");
		RedisCache.remove(CacheContants.fishing_rule_public_list+"experience");
		return MsgFactory.success();
	}

	/**
	 * 清除清除个人规则缓存
	 * 
	 * @return
	 */
	@RequestMapping(value = "/removePrivateCaching", method = RequestMethod.GET)
	@Permission("fishing_rule_remove_private")
	@JSON
	public MsgDto<String> removePrivateCaching(String userId) {
		List<UserFishingRule> lists = fishingRuleService.getUserId();
		for (UserFishingRule ufr : lists) {
			RedisCache.remove(CacheContants.fishing_rule_private_list + ufr.getUserId()+"gold");
			RedisCache.remove(CacheContants.fishing_rule_private_list + ufr.getUserId()+"experience");
		}
		return MsgFactory.success();
	}

}
