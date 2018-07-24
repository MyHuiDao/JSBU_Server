package com.huidao.admin.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.User;
import com.huidao.common.entity.UserLogin;
import com.huidao.common.interfaces.admin.IStatisticsService;
import com.huidao.common.interfaces.admin.IUserLoginService;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

@Controller
@RequestMapping("/statistics")
public class StatisticsController {

	@Reference
	private IUserLoginService userLoginService;
	@Reference
	private IStatisticsService statisticsService;

	/**
	 * 获取用户十五天的登录数据
	 * 
	 * @return
	 */
	@RequestMapping("/getUserLoginAllData")
	@Permission("system_statistics_userLogin")
	@JSON
	public MsgDto<Map<String, List<UserLogin>>> getUserLoginAllData(Date startDate, Date endDate) {
		return userLoginService.getUserLoginAllData(startDate, endDate);
	}

	/**
	 * 获取指定日期数据
	 * 
	 * @param date
	 * @return
	 */
	@RequestMapping("/getSpecifiedData")
	@Permission
	@JSON
	public List<UserLogin> getSpecifiedData(String date) {
		return userLoginService.getSpecifiedData(date);
	}

	/**
	 * 分页显示用户数据
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping("/findUserLoginAll")
	@Permission("statistics_userLogin_get")
	@JSON
	public MsgDto<Page<UserLogin>> findUserLoginAll(Date startDate, Date endDate) {
		return userLoginService.findUserLoginAll(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(), startDate,
				endDate);
	}

	/**
	 * 统计用户注册数据
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping("/countUserRegistData")
	@Permission("system_statistics_user_added")
	@JSON
	public MsgDto<Map<String, List<User>>> countUserRegistData(String startDate, String endDate) {

		return statisticsService.countUserRegistData(startDate, endDate);
	}

	@RequestMapping("/findUserOnlineDuration")
	@JSON
	public MsgDto<Page<User>> findUserOnlineDuration(String code) {
		return statisticsService.findUserOnlineDuration(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(),
				code);
	}

	@RequestMapping("/findUserRegistData")
	@JSON
	public MsgDto<Page<User>> findUserRegistData(String code) {

		return statisticsService.findUserRegistData(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(), code);
	}

}
