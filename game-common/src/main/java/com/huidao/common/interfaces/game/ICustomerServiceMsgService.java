package com.huidao.common.interfaces.game;

import java.util.List;

import com.huidao.common.entity.CustomerServiceMsg;
import com.huidao.common.entity.User;
import com.huidao.common.msg.MsgDto;

/**
 * 在线客服消息
 * 
 * @author Administrator
 *
 */
public interface ICustomerServiceMsgService {
	/**
	 * 保存消息
	 * 
	 * @param customerServiceMsg
	 * @return
	 */
	public MsgDto<Boolean> save(CustomerServiceMsg customerServiceMsg);

	/**
	 * 更新状态
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	public MsgDto<Boolean> update(String id, int status);

	/**
	 * 游戏用户查询未接受到消息
	 * 
	 * @return
	 */
	public MsgDto<List<CustomerServiceMsg>> userFindNoSendMsg(String userId);

	/**
	 * 客服查询未接受到的消息
	 * 
	 * @return
	 */
	public MsgDto<List<CustomerServiceMsg>> sysUserFindNoSendMsg(String userId);

	/**
	 * 查询所有消息未接受到的用户
	 * 
	 * @return
	 */
	public MsgDto<List<User>> findUserNoSendMsg();
}
