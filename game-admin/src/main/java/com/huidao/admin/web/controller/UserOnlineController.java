package com.huidao.admin.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.UserOnline;
import com.huidao.common.interfaces.admin.IUserOnlineService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;

@Controller
@RequestMapping("/userOnline")
public class UserOnlineController {

	@Reference
	private IUserOnlineService userOnlineService;

	/**
	 * 显示用户在线数据信息
	 * 
	 * @param date
	 * @return
	 */
	@RequestMapping("/statisticsUserOnline")
	@Permission("user_online_get")
	@JSON
	public MsgDto<List<UserOnline>> statisticsUserOnline(String startDate, String endDate) {
		return MsgFactory.success(userOnlineService.statisticsUserOnline(startDate, endDate));
	}

}
