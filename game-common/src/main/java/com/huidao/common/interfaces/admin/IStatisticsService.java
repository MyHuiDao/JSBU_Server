package com.huidao.common.interfaces.admin;

import java.util.List;
import java.util.Map;

import com.huidao.common.entity.User;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

/**
 * 数据统计统一接口
 * 
 * @author lenovo
 *
 */
public interface IStatisticsService {
	/**
	 * 统计用户注册信息
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public MsgDto<Map<String,List<User>>> countUserRegistData(String startTime, String endTime);

	/**
	 * 查询新增注册用户
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public MsgDto<Page<User>> findUserRegistData(Integer page,Integer size,String code);

	/**
	 * 显示用户在线时长数据
	 * 
	 * @return
	 */
	public MsgDto<Page<User>> findUserOnlineDuration(Integer page, Integer size, String code);

}
