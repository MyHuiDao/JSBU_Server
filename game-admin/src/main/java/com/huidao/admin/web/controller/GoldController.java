package com.huidao.admin.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.admin.web.manager.SysUserManager;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.SysUser;
import com.huidao.common.enums.GameType;
import com.huidao.common.enums.GoldSourceType;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;

@Controller
@RequestMapping("/gold")
public class GoldController {

	@Reference
	private IUserService userService;

	@RequestMapping("/getSourceType")
	@JSON
	@Permission
	public MsgDto<List<Map<String, String>>> getSourceType() {
		List<Map<String, String>> list = new ArrayList<>();
		for (int i = 0; i < GoldSourceType.values().length; i++) {
			Map<String, String> map = new HashMap<>();
			map.put(GoldSourceType.values()[i].toString(), GoldSourceType.values()[i].getDesc());
			list.add(map);
		}
		return MsgFactory.success(list);
	}

	@RequestMapping("/modifyGold")
	@JSON
	@Permission("user_gold_adjustment")
	public MsgDto<String> modifyGold(String sourceType, String userId, Integer gold, String desc, Integer freeGold,
			Double money, Integer type) {
		SysUser sysUser = SysUserManager.getSysUser();
		GoldSourceType goldSourceType = GoldSourceType.valueOf(sourceType);
		if(goldSourceType.equals(GoldSourceType.pay)) {
				if(gold<0) {
					throw new RuntimeException("充值金币不能为负数");
				}
		}
		if (gold > 0) {
			if (goldSourceType.equals(GoldSourceType.pay)) {
				return userService.payGold(userId, gold, freeGold, money, desc, sysUser.getId(), type);
			}
			return userService.addGold(userId, gold, goldSourceType, desc, GameType.admin, sysUser.getId());
		} else {
			return userService.reduceGold(userId, Math.abs(gold), goldSourceType, desc, GameType.admin,
					sysUser.getId());
		}
	}

}
