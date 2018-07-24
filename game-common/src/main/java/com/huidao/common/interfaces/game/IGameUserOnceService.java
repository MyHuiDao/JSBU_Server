package com.huidao.common.interfaces.game;

import com.huidao.common.enums.GameUserOnceType;
import com.huidao.common.msg.MsgDto;

/**
 * 玩家首次操作
 * @author Administrator
 *
 */
public interface IGameUserOnceService {

	/**
	 * 添加玩家首次操作
	 * @param userId
	 * @param onceType
	 * @return
	 */
	public MsgDto<String> addGameUserOnce(String userId,GameUserOnceType onceType);
	
	
	/**
	 * 是否第一次操作
	 * @param userId
	 * @param onceType
	 * @return
	 */
	public MsgDto<Boolean> isGameUserOnce(String userId,GameUserOnceType onceType);
}
