package com.huidao.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.admin.web.manager.SysUserManager;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.GameSetting;
import com.huidao.common.entity.SysUser;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

@Controller
@RequestMapping("/setting")
public class GameSettingController {
	@Reference
	private IGameSettingService gameSettingService;

	/**
	 * 获取单个游戏设置对象
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getSettingById", method = RequestMethod.GET)
	@Permission("game_setting_view")
	@JSON
	public MsgDto<GameSetting> getSettingById(String id) {
		return gameSettingService.getSettingById(id);
	}

	/**
	 * 获取值
	 * 
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/getValue", method = RequestMethod.GET)
	@Permission("game_setting_view")
	@JSON
	public MsgDto<String> getValue(String key) {
		return MsgFactory.success(gameSettingService.getValue(key));
	}

	/**
	 * 分页查询游戏设置
	 * 
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/findPage", method = RequestMethod.GET)
	@Permission("game_settings")
	@JSON
	public MsgDto<Page<GameSetting>> findPage(String key) {
		return gameSettingService.findPage(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(), key);
	}

	/**
	 * 游戏设置更新
	 * 
	 * @param gameSetting
	 * @return
	 */
	@RequestMapping(value = "/updSetting", method = RequestMethod.POST)
	@Permission("game_setting_update")
	@JSON
	public MsgDto<String> updSetting(GameSetting gameSetting) {
		SysUser getsysUser = SysUserManager.getSysUser();
		return gameSettingService.update(getsysUser.getAccount(), gameSetting);
	}

	/**
	 * 刷新值
	 * 
	 * @param gameSetting
	 * @return
	 * @return
	 */
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	@JSON
	public MsgDto<String> refresh(String key) {
		gameSettingService.refresh(key);
		return MsgFactory.success();
	}

	/**
	 * 通过key获取GameSetting
	 * 
	 * @param key
	 * @return
	 */
	@RequestMapping(value = "/getKeyGameSetting", method = RequestMethod.GET)
	@Permission("game_setting_view")
	@JSON
	public MsgDto<GameSetting> getKeyGameSetting(String key) {

		return MsgFactory.success(gameSettingService.getKeyGameSetting(key));
	}

}
