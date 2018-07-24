package com.huidao.common.interfaces.match.fish;

import java.util.Map;

import com.huidao.common.entity.MatchFishOpenPrize;
import com.huidao.common.msg.MsgDto;

public interface IOpenPrizeService {
	/**
	 * 查找所有未开奖的记录
	 * 
	 * @return
	 */
	public MatchFishOpenPrize getNoOpen();

	/**
	 * 修改开奖的状态
	 * 
	 * @param matchFishOpenPrize
	 * @return
	 */
	public MsgDto<Boolean> update(MatchFishOpenPrize matchFishOpenPrize);
	
	
	/**
	 * 开奖
	 * 
	 * @param matchFishOpenPrize
	 * @return
	 */
	public MsgDto<Map<String, Object>> openPrize(MatchFishOpenPrize matchFishOpenPrize,boolean flag);

}
