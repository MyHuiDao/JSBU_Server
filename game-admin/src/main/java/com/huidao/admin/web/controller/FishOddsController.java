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
import com.huidao.common.entity.FishOdds;
import com.huidao.common.interfaces.admin.IFishOddsService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;

/**
 * 捕鱼赔率控制层
 * 
 * @author lenovo
 *
 */
@Controller
@RequestMapping("/fishOdds")
public class FishOddsController {

	@Reference
	private IFishOddsService fishOddsService;

	/**
	 * 获取捕鱼赔率所有信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getFishOddsAll", method = RequestMethod.GET)
	@Permission("fish_odds_get")
	@JSON
	public MsgDto<List<FishOdds>> getFishOddsAll() {

		return MsgFactory.success(fishOddsService.getFishOddsAll());
	}

	/**
	 * 添加捕鱼赔率
	 * 
	 * @param fishOdds
	 * @return
	 */
	@RequestMapping(value = "/addFishOdds", method = RequestMethod.POST)
	@Permission("fish_odds_add")
	@JSON
	public MsgDto<String> addFishOdds(FishOdds fishOdds) {
		return fishOddsService.add(fishOdds);
	}

	/**
	 * 修改捕鱼赔率
	 * 
	 * @param fishOdds
	 * @return
	 */
	@RequestMapping(value = "/updateFishOdds", method = RequestMethod.POST)
	@Permission("fish_odds_update")
	@JSON
	public MsgDto<String> updateFishOdds(FishOdds fishOdds) {

		return fishOddsService.update(fishOdds);
	}

	/**
	 * 删除捕鱼赔率
	 * 
	 * @param fishOdds
	 * @return
	 */
	@RequestMapping(value = "/deleteFishOdds", method = RequestMethod.GET)
	@Permission("fish_odds_delete")
	@JSON
	public MsgDto<String> deleteFishOdds(String id) {

		return fishOddsService.delete(id);
	}

	/**
	 * 获取捕鱼赔率
	 * 
	 * @param fishOdds
	 * @return
	 */
	@RequestMapping(value = "/getFishOdds", method = RequestMethod.GET)
	@Permission("fish_odds_get")
	@JSON
	public MsgDto<FishOdds> getFishOdds(String id) {
		return MsgFactory.success(fishOddsService.get(id));
	}

	/**
	 * 删除缓存
	 * 
	 * @return
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	@Permission("fish_odds_remove")
	@JSON
	public MsgDto<String> remove() {
		RedisCache.remove(CacheContants.fish_odds_list);
		return MsgFactory.success();
	}

}
