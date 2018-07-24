package com.huidao.common.interfaces.admin;

import java.util.List;

import com.huidao.common.entity.AgenApply;
import com.huidao.common.msg.MsgDto;

/**
 * 代理商申请接口
 * 
 * @author lenovo
 *
 */
public interface IAgenApplyService {
	/**
	 * 添加代理商申请
	 * 
	 * @param agenApply
	 * @return
	 */
	public MsgDto<String> add(AgenApply agenApply);

	/**
	 * 获取未审核的代理商
	 * 
	 * @param auditStatus
	 *            申请状态
	 * @return
	 */
	public List<AgenApply> findAgenApplyAll(Object auditStatus);

	/**
	 * 修改代理商状态
	 * 
	 * @param agenApply
	 *            代理商
	 * @return
	 */
	public MsgDto<String> update(AgenApply agenApply);

	/**
	 * 判断用户是否存在
	 * 
	 * @param account
	 * @return
	 */
	public AgenApply getAgenApply(String account);

	/**
	 * 获取所有的代理用户
	 * 
	 * @return
	 */
	public List<AgenApply> getAgenApplyAll();

	/**
	 * 通过类型判断账号是否已申请
	 * 
	 * @param userId
	 * @param type
	 * @return
	 */
	public AgenApply getAgenApplyType(String userId, Integer auditStatus);

}
