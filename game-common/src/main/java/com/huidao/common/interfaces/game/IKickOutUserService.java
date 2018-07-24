package com.huidao.common.interfaces.game;

import com.huidao.common.msg.MsgDto;

public interface IKickOutUserService {

	/**
	 * 踢出用户
	 * 
	 * @param userId
	 * @return
	 */
	public MsgDto<String> kickOutUser(String userId);

	/**
	 * 踢出所有用户
	 * 
	 * @return
	 */
	public MsgDto<String> kickOutUserAll();

}
