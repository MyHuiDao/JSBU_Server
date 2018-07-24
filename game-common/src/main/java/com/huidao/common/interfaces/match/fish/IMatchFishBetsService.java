package com.huidao.common.interfaces.match.fish;

import java.util.List;

import com.huidao.common.entity.MatchFishBets;
import com.huidao.common.msg.MsgDto;

public interface IMatchFishBetsService {

	/**
	 * 保存用户下单记录
	 * 
	 * @param matchFishOpenPrize
	 * @return
	 */
	public MsgDto<Integer> saveMatchFishBets(MatchFishBets matchFishBets);

	/**
	 * 获取用户下单记录
	 * 
	 * @param openPrizeId
	 * @param userId
	 * @return
	 */
	public List<MatchFishBets> getUserMatchFishBets(String openPrizeId, String userId);

}
