package com.huidao.common.interfaces.fish.slot.machine;

import java.util.List;

import com.huidao.common.entity.FishMagnificationDeploy;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

public interface IFishMagnificationDeployService {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public MsgDto<Page<FishMagnificationDeploy>> findFishMagnificationDepoyAll(Integer page, Integer size);

	/**
	 * 获取所有的鱼种类
	 * 
	 * @return
	 */
	public MsgDto<List<FishMagnificationDeploy>> findAll();

	/**
	 * 添加
	 * 
	 * @param fishMagnificationDeploy
	 * @return
	 */
	public MsgDto<Integer> add(FishMagnificationDeploy fishMagnificationDeploy);

	/**
	 * 修改
	 * 
	 * @param fishMagnificationDeploy
	 * @return
	 */
	public MsgDto<Integer> update(FishMagnificationDeploy fishMagnificationDeploy);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<Integer> delete(String id);

	/**
	 * 获取
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<FishMagnificationDeploy> getFishMagnificationDeploy(String id);

}
