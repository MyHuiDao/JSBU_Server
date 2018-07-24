package com.huidao.admin.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.entity.GameFish;
import com.huidao.common.interfaces.admin.IGameFishService;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;

/**
 * 鱼种类控制
 * 
 * @author lenovo
 *
 */
@Controller
@RequestMapping("/gameFish")
public class GameFishController {
	@Reference
	private IGameFishService gameFishService;

	@Reference
	private IGameSettingService gameSettingService;

	/**
	 * 显示所有鱼种类
	 * 
	 * @return
	 */
	@RequestMapping("/findGameFishData")
	@Permission("game_setting_game_fish")
	@JSON
	public MsgDto<List<GameFish>> findGameFishData() {

		return MsgFactory.success(gameFishService.findGameFishData());
	}

	/**
	 * 添加鱼种类
	 * 
	 * @param gameFish
	 * @return
	 */
	@RequestMapping("/addGameFish")
	@Permission
	@JSON
	public MsgDto<String> addGameFish(GameFish gameFish) {

		return gameFishService.add(gameFish);
	}

	/**
	 * 修改鱼种类
	 * 
	 * @param gameFish
	 * @return
	 */
	@RequestMapping("/updateGameFish")
	@Permission("game_fish_update")
	@JSON
	public MsgDto<String> updateGameFish(GameFish gameFish) {

		return gameFishService.update(gameFish);
	}

	/**
	 * 删除鱼种类
	 * 
	 * @param gameFish
	 * @return
	 */
	@RequestMapping("/deleteGameFish")
	@Permission
	@JSON
	public MsgDto<String> deleteGameFish(String id) {

		return gameFishService.delete(id);
	}

	/**
	 * 获取鱼种类
	 * 
	 * @param gameFish
	 * @return
	 */
	@RequestMapping("/getGameFish")
	@Permission
	@JSON
	public MsgDto<GameFish> getGameFish(String id) {

		return MsgFactory.success(gameFishService.getGameFish(id));
	}

	@RequestMapping("/refreshFish")
	@Permission("game_fish_reset")
	@JSON
	public MsgDto<String> refreshFish() {
		/*gameSettingService.refresh(GameSettingContants.fish_probability);*/
		RedisCache.remove(CacheContants.fish_info_list);
		return MsgFactory.success();
	}

}
