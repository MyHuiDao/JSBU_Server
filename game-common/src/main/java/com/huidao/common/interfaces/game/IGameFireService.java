package com.huidao.common.interfaces.game;

import java.util.List;

import com.huidao.common.entity.GameFire;
import com.huidao.common.msg.MsgDto;

public interface IGameFireService {
	/**
	 * 获取所有炮的权重
	 * 
	 * @return
	 */
	public MsgDto<List<GameFire>> findAll();

	/**
	 * 刷新缓存
	 * 
	 * @return
	 */
	public MsgDto<String> refreshCache();

	/**
	 * 修改鱼权重
	 * 
	 * @param gameFire
	 * @return
	 */
	public MsgDto<Integer> updateGameFire(GameFire gameFire);

}
