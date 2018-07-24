package com.huidao.common.interfaces.admin;

import java.util.List;

import com.huidao.common.entity.UserFishingRule;
import com.huidao.common.msg.MsgDto;

/**
 * 用户捕鱼规则关联表
 * 
 * @author lenovo
 *
 */
public interface IUserFishingRuleService {

	/**
	 * 获取用户所有捕鱼规则
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<List<UserFishingRule>> getUserFishingRuleData(String id);

	/**
	 * 添加用户捕鱼规则关联
	 * 
	 * @param userFishingRule
	 * @return
	 */
	public MsgDto<String> add(UserFishingRule userFishingRule);

	/**
	 * 修改用户捕鱼规则关联
	 * 
	 * @param userFishingRule
	 * @return
	 */
	public MsgDto<String> update(String id, Integer status);

	/**
	 * 通过用户id删除所有规则
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<String> delete(String id);

}
