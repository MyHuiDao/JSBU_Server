package com.huidao.common.interfaces.admin;

import com.huidao.common.entity.FishControllerProperty;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

public interface IFishControllerPropertyService {

	/**
	 * 分页显示
	 * 
	 * @param page
	 * @param size
	 * @param type
	 * @return
	 */
	public MsgDto<Page<FishControllerProperty>> findFishControllerProperty(Integer page, Integer size, Integer type);

	/**
	 * 添加可控数值
	 * 
	 * @param fishControllerProperty
	 * @return
	 */
	public MsgDto<Integer> add(FishControllerProperty fishControllerProperty, String userId, String gameId);

	/**
	 * 修改可控数值
	 * 
	 * @param fishControllerProperty
	 * @return
	 */
	public MsgDto<Integer> update(String id, Double wholeDangWei, Double playerDangWei, Double fishDangWei,
			Double fuDuDangWei);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<Integer> delete(String id);

	/**
	 * 获取可控数值
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<FishControllerProperty> getFishControllerProperty(String id);

	/**
	 * 通过用户id获取捕鱼数值
	 * 
	 * @param userId
	 * @return
	 */
	public MsgDto<FishControllerProperty> getUserFishControllerProperty(String userId, String gameId);

	/**
	 * 通过userId、gameId、type删除
	 * 
	 * @param userId
	 * @param gameId
	 * @param type
	 * @return
	 */
	public MsgDto<Integer> deleteUserGameFishControllerProperty(String userId, String gameId, Integer type);

}
