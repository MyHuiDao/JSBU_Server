package com.huidao.common.interfaces.admin;

import com.huidao.common.entity.DangerGetCashUser;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

public interface IDangerGetCashUserService {
	/**
	 * 分页显示所有高危用户的记录
	 * 
	 * @param page
	 * @param size
	 * @param code
	 * @return
	 */
	public MsgDto<Page<DangerGetCashUser>> getDangerGetCashUserAll(Integer page, Integer size, String code);

	/**
	 * 分页显示高危用户解除历史记录
	 * 
	 * @param page
	 * @param size
	 * @param code
	 * @return
	 */
	public MsgDto<Page<DangerGetCashUser>> getDangerGetCashUserSelf(Integer page, Integer size, String userId);

	/**
	 * 获取高危用户记录
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<Integer> release(String id, String sysUserId);
}
