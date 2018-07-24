package com.huidao.common.interfaces.admin;

import java.util.List;

import com.huidao.common.entity.UserOnline;

/**
 * 统计在线用户数据
 * 
 * @author lenovo
 *
 */
public interface IUserOnlineService {
	/**
	 * 统计用户在线数据
	 * 
	 * @param date
	 * @return
	 */
	public List<UserOnline> statisticsUserOnline(String startDate, String endDate);
}
