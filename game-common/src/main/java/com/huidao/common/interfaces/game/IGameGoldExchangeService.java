package com.huidao.common.interfaces.game;

import java.util.List;

import com.huidao.common.entity.GameGoldExchange;
import com.huidao.common.msg.MsgDto;

public interface IGameGoldExchangeService {
	
	
	/**
	 * 获取rmb对应金币
	 * @return
	 */
	public MsgDto<List<GameGoldExchange>> getExChangeGoldList(String userId,Integer type);
}
