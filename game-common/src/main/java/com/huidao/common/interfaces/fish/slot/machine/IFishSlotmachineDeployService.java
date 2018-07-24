package com.huidao.common.interfaces.fish.slot.machine;

import com.huidao.common.entity.FishSlotmachineDeploy;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

public interface IFishSlotmachineDeployService {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public MsgDto<Page<FishSlotmachineDeploy>> findFishSlotmachineDeployAll(Integer page, Integer size);

	/**
	 * 添加
	 * 
	 * @param fishSlotmachineDeploy
	 * @return
	 */
	public MsgDto<String> add(FishSlotmachineDeploy fishSlotmachineDeploy);

	/**
	 * 修改
	 * 
	 * @param fishSlotmachineDeploy
	 * @return
	 */
	public MsgDto<Integer> update(FishSlotmachineDeploy fishSlotmachineDeploy);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<Integer> delete(String id);

	/**
	 * 获取配置信息
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<FishSlotmachineDeploy> get(String id);

}
