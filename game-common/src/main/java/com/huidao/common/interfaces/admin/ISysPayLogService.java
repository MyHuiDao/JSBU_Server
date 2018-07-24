package com.huidao.common.interfaces.admin;

import com.huidao.common.entity.PayLog;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

public interface ISysPayLogService {

	/**
	 * 分页显示充值记录
	 * 
	 * @param gameUserId
	 *            用户id
	 * @param status
	 *            充值状态
	 * @param createDate
	 *            订单创建时间
	 * @return
	 */
	public MsgDto<Page<PayLog>> findPayLogAll(Integer page, Integer size, String code, String status);

}
