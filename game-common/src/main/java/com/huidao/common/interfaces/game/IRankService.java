package com.huidao.common.interfaces.game;

import java.util.List;

import com.huidao.common.dto.RankDto;
import com.huidao.common.msg.MsgDto;

/**
 * 排行榜
 * @author Administrator
 *
 */
public interface IRankService {
	
	/**
	 * 
	 * @param dateType 0:今日,1：昨日
	 * @return
	 */
	public MsgDto<List<RankDto>> findRankActiveByDate(Integer dateType);
	
	
	/**
	 * 
	 * @param dateType 0:今日,1：昨日
	 * @return
	 */
	public MsgDto<List<RankDto>> findRankPayByDate(Integer dateType);

}
