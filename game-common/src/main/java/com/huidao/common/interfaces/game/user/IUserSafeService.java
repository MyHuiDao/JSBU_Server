package com.huidao.common.interfaces.game.user;

import com.huidao.common.msg.MsgDto;

/**
 * 保险箱功能
 * 
 * @author Administrator
 *
 */
public interface IUserSafeService {

	/**
	 * 保险箱存入
	 * 
	 * @return
	 */
	public MsgDto<String> userSafeIn(String userId, Integer  gold);

	/**
	 * 保险柜取出
	 * 
	 * @param gold
	 * @return
	 */
	public MsgDto<String> userTakeOut(String userId,Integer gold);

	/**
	 * 获取保险柜数量
	 * @param id
	 * @return
	 */
	public MsgDto<Integer> userSafeGet(String id);
}
