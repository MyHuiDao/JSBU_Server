package com.huidao.admin.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.entity.GameArea;
import com.huidao.common.entity.GameAreaRule;
import com.huidao.common.interfaces.admin.IGameAreaService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;

@Controller
@RequestMapping("/gameArea")
public class GameAreaController {
	@Reference
	private IGameAreaService gameAreaService;

	@RequestMapping("/findGameAreaAll")
	@Permission("game_area_get")
	@JSON
	public MsgDto<List<GameArea>> findGameAreaAll() {

		return gameAreaService.findGameAreaAll();
	}

	@RequestMapping("/addGameArea")
	@Permission("game_area_add")
	@JSON
	public MsgDto<Integer> addGameArea(GameArea gameArea) {
		return gameAreaService.add(gameArea);
	}

	@RequestMapping("/updateGameArea")
	@Permission("game_area_update")
	@JSON
	public MsgDto<Integer> updateGameArea(GameArea gameArea) {

		return gameAreaService.update(gameArea);
	}

	@RequestMapping("/deleteGameArea")
	@Permission("game_area_delete")
	@JSON
	public MsgDto<Integer> deleteGameArea(String id) {

		return gameAreaService.delete(id);
	}

	@RequestMapping("/getGameArea")
	@Permission("game_area_get")
	@JSON
	public MsgDto<GameArea> getGameArea(String id) {

		return gameAreaService.get(id);
	}

	/**
	 * 显示该游戏区有的规则
	 * 
	 * @param areaId
	 * @return
	 */
	@RequestMapping("/getFishingRule")
	@Permission
	@JSON
	public MsgDto<List<GameArea>> getFishingRule(String areaId) {
		return gameAreaService.getFishingRule(areaId);
	}

	/**
	 * 添加游戏区规则
	 * 
	 * @param gameAreaRule
	 * @return
	 */
	@RequestMapping("/addGameAreaRule")
	@Permission("game_area_rule_add")
	@JSON
	public MsgDto<Integer> addGameAreaRule(GameAreaRule gameAreaRule) {
		return gameAreaService.addFishingRule(gameAreaRule);
	}

	/**
	 * 删除该游戏区规则
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteGameAreaRule")
	@Permission("game_area_rule_delete")
	@JSON
	public MsgDto<Integer> deleteGameAreaRule(String id) {

		return gameAreaService.deleteGameAreaRule(id);
	}

	/**
	 * 刷新捕鱼房间规则
	 * 
	 * @return
	 */
	@RequestMapping("/refreshAreaRule")
	@JSON
	public MsgDto<String> refreshAreaRule() {
		List<GameArea> data = gameAreaService.findGameAreaAll().getData();
		for (GameArea ga : data) {
			RedisCache.remove(CacheContants.fishing_rule_room_list + ga.getId()+"gold");
			RedisCache.remove(CacheContants.fishing_rule_room_list + ga.getId()+"experience");
		}
		return MsgFactory.success();
	}

}
