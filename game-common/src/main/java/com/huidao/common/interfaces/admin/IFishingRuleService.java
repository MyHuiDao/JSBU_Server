package com.huidao.common.interfaces.admin;

import java.util.List;

import com.huidao.common.entity.FishingRule;
import com.huidao.common.entity.UserFishingRule;
import com.huidao.common.msg.MsgDto;

public interface IFishingRuleService {

	/**
	 * 获取公共规则
	 * 
	 * @return
	 */
	public MsgDto<List<FishingRule>> findPublicFishingRuleAll(String roomType);
	
	
	/**
	 * 获取房间规则
	 * 
	 * @return
	 */
	public MsgDto<List<FishingRule>> findRoomFishingRuleAll(String areaId,String roomType);
	
	/**
	 * 获取私人规则
	 * 
	 * @return
	 */
	public MsgDto<List<FishingRule>> findPrivateFishingRuleByUserId(String userId,String roomType);

	/**
	 * 显示所有的捕鱼规则
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public List<FishingRule> findPageFishingRule(Integer type);

	/**
	 * 添加捕鱼规则
	 * 
	 * @param fishingRule
	 * @return
	 */
	public MsgDto<String> add(FishingRule fishingRule);

	/**
	 * 修改捕鱼规则
	 * 
	 * @param fishingRule
	 * @return
	 */
	public MsgDto<String> update(FishingRule fishingRule);

	/**
	 * 删除捕鱼规则
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<String> delete(String id);

	/**
	 * 获取捕鱼规则
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<FishingRule> getFishingRule(String id);

	/**
	 * 获取私人规则的用户id
	 * 
	 * @return
	 */
	public List<UserFishingRule> getUserId();
}
