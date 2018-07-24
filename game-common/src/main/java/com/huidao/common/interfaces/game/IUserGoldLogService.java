package com.huidao.common.interfaces.game;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.huidao.common.dto.GetOutGoldDto;
import com.huidao.common.entity.UserGoldLog;
import com.huidao.common.enums.GoldSourceType;
import com.yehebl.orm.data.common.dto.Page;

public interface IUserGoldLogService {

	/**
	 * 获取成本与当前金币
	 * @param userId
	 * @return
	 */
	public GetOutGoldDto getGetOutGold(String userId);
	
	
	/**
	 * 获取当前体验币和默认体验币
	 * @param userId
	 * @return
	 */
	public GetOutGoldDto getGetOutExperience(String userId);

	/**
	 * 获取相关用户的金币情况
	 * 
	 * @param userId
	 * @return
	 */
	public Page<UserGoldLog> findUserGoldLogAll(Integer page, Integer size, String userId, String sourceType,String gameType);

	/**
	 * 通过用户id获取用户金币使用情况
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserGoldLog> findUserGoldLogData(Integer size, String userId, String startDate, String endDate,String gameType);

	
	/**
	 * 通过用户id 类型 获取金币相关的统计
	 * @param userId
	 * @param gst
	 * @return
	 */
	public BigDecimal getSumGoldByType(String userId, GoldSourceType gst);
	
	/**
	 * 通过用户id 类型 获取金币相关的统计
	 * @param userId
	 * @param gst
	 * @return
	 */
	public BigDecimal getSumGoldByType(String userId, GoldSourceType gst,Date startDate,Date endDate);

	

}
