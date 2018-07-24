package com.huidao.admin.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.UserGoldLog;
import com.huidao.common.enums.GoldSourceType;
import com.huidao.common.interfaces.game.IUserGoldLogService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

@Controller
@RequestMapping("/sysUserGoldLog")
public class SysUserGoldLogController {
	@Reference
	private IUserGoldLogService userGoldLogService;

	/**
	 * 分页显示用户金币消耗数据
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getUserGoldLogData")
	@Permission("user_gold_log_get")
	@JSON
	public MsgDto<Page<UserGoldLog>> getUserGoldLogData(String userId, String sourceType,String gameType) {
		return MsgFactory.success(userGoldLogService.findUserGoldLogAll(PageUtil.getPage().getPage(),
				PageUtil.getPage().getSize(), userId, sourceType,gameType));
	}

	/**
	 * 获取金币来源
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSourceType", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<List<String>> getSourceType() {
		List<String> list = new ArrayList<String>();
		for (GoldSourceType gst : GoldSourceType.values()) {
			list.add(gst.getDesc());
		}
		return MsgFactory.success(list);
	}

	/**
	 * 统计用户金币使用情况
	 */
	@RequestMapping(value = "/findUserGoldLogData", method = RequestMethod.POST)
	@Permission("user_gold_log_get")
	@JSON
	public MsgDto<List<UserGoldLog>> findUserGoldLogData(String userId, String startDate, String endDate,String gameType) {

		return MsgFactory.success(userGoldLogService.findUserGoldLogData(20, userId, startDate, endDate,gameType));
	}
}
