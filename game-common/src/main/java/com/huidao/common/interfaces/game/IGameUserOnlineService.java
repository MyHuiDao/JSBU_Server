package com.huidao.common.interfaces.game;

import com.huidao.common.enums.GameType;

/**
 * 游戏在线时长统计
 * @author Administrator
 *
 */
public interface IGameUserOnlineService {
	
	/**
	 * 开始游戏时间
	 * @param type
	 * @param userId
	 */
	public void startGame(GameType type,String userId);
	
	/**
	 * 结束游戏时间
	 * @param type
	 * @param userId
	 */
	public void endGame(GameType type,String userId);

}
