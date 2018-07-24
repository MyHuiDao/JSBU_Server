package com.huidao.common.interfaces.admin;


import com.huidao.common.entity.UserShare;
import com.huidao.common.msg.MsgDto;

public interface IUserShareService {
	/**
	 * 获取用户分享所有的数据
	 * 
	 * @return
	 */
	public Integer findUserShareAll(String userId);

	/**
	 * 添加用户分享数据
	 * 
	 * @param userShare
	 * @return
	 */
	public MsgDto<String> add(UserShare userShare);
}
