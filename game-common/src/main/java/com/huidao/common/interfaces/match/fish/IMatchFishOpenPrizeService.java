package com.huidao.common.interfaces.match.fish;

import java.util.Date;
import java.util.List;

import com.huidao.common.entity.MatchFishOpenPrize;

public interface IMatchFishOpenPrizeService {

	/**
	 * 通过房间id生成开奖记录
	 * 
	 * @param roomId
	 * @return
	 */
	public String insterMatchFishOpenPrize(String roomId);

	/**
	 * 获取开奖记录
	 * 
	 * @param page
	 * @param size
	 * @param roomId
	 * @param createDate
	 * @return
	 */
	public List<MatchFishOpenPrize> findMatchFishOpenPrize(Integer page, Integer size, String roomId,
			Date createDate);

}
