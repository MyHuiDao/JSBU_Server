package com.huidao.common.interfaces.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.huidao.common.entity.UserLogin;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

public interface IUserLoginService {

	/**
	 * 分页用户登录数据
	 * 
	 * @param date
	 * @return
	 */
	public MsgDto<Page<UserLogin>> findUserLoginAll(Integer page, Integer size, Date startDate, Date endDate);

	/**
	 * 统计用户15天的登录数据
	 * 
	 * @return
	 */
	public MsgDto<Map<String, List<UserLogin>>> getUserLoginAllData(Date startDate, Date endDate);

	/**
	 * 获取指定日期的用户数据
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public List<UserLogin> getSpecifiedData(String date);

}
