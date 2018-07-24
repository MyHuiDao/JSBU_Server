
package com.huidao.game.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.entity.GameArea;
import com.huidao.common.entity.GameNotice;
import com.huidao.common.interfaces.game.IGameAreaService;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.game.notice.IGameNoticeService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;

/**
 * 游戏相关区
 * 
 * @author lenovo
 *
 */
@Controller
@RequestMapping("/game")
public class GameController {

	@Reference
	private IGameAreaService gameAreaService;

	@Reference
	private IGameNoticeService gameNoticeService;

	@Reference
	private IGameSettingService gameSettingService;

	@RequestMapping(value = "/area/{type}", method = RequestMethod.GET)
	@JSON
	@Permission
	public MsgDto<List<GameArea>> getGameArea(@PathVariable String type) {
		return gameAreaService.findGameAreaType(type);
	}

	/**
	 * 游戏大厅公告
	 * 
	 * @return
	 */
	@RequestMapping(value = "/gameNotice", method = RequestMethod.GET)
	@Permission
	@JSON(clazz = { GameNotice.class }, property = { "centen" })
	public MsgDto<List<GameNotice>> gameNotice() {
		return gameNoticeService.findByType("0");
	}

	/**
	 * 捕鱼游戏规则
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getFishingGameRule", method = RequestMethod.GET)
	@Permission
	@JSON(clazz = { GameNotice.class }, property = { "centen" })
	public MsgDto<String> getFishingGameRule() {
		return MsgFactory.success(gameSettingService.getValue(GameSettingContants.fishing_game_rule));
	}

	/**
	 * 捕鱼游戏规则
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getCustmoterWeixin", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<String[]> getCustmoterWeixin() {
		String value = gameSettingService.getValue(GameSettingContants.customer_weixin);
		String[] v = value.split(",");
		return MsgFactory.success(v);
	}

	/**
	 * 获取app版本
	 * 
	 * @return
	 */
	@RequestMapping("/getVersion")
	@JSON
	public MsgDto<Map<String, Object>> getVersion() {
		String version = gameSettingService.getValue(GameSettingContants.app_version);
		String url = gameSettingService.getValue(GameSettingContants.app_url);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", url);
		map.put("version", version);
		return MsgFactory.success(map);
	}

	/**
	 * 获取app版本
	 * 
	 * @return
	 */
	@RequestMapping("/getIosVersion")
	@JSON
	public MsgDto<Map<String, Object>> getIosVersion() {
		String version = gameSettingService.getValue(GameSettingContants.app_ios_version);
		String url = gameSettingService.getValue(GameSettingContants.app_ios_url);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", url);
		map.put("version", version);
		return MsgFactory.success(map);
	}

	/**
	 * 开关
	 * 
	 * @return
	 */
	@RequestMapping("/getSwitch")
	@JSON
	public MsgDto<String> getSwitch() {
		String switchVal = gameSettingService.getValue(GameSettingContants.game_switch);
		if (switchVal.equals("Y")) {
			return MsgFactory.success();
		}
		return MsgFactory.fail();
	}

}