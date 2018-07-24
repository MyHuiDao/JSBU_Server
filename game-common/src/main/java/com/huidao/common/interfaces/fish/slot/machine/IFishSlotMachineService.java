package com.huidao.common.interfaces.fish.slot.machine;

import java.util.List;

import com.huidao.common.entity.FishSlotMachine;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

/**
 * 鱼类老虎机鱼类接口
 * 
 * @author Administrator
 *
 */
public interface IFishSlotMachineService {
	/**
	 * 获取所有鱼类
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public MsgDto<Page<FishSlotMachine>> findFishSlotMachineAll(Integer page, Integer size);

	/**
	 * 添加
	 * 
	 * @param fishSlotMachine
	 * @return
	 */
	public MsgDto<Integer> add(FishSlotMachine fishSlotMachine);

	/**
	 * 修改
	 * 
	 * @param fishSlotMachine
	 * @return
	 */
	public MsgDto<Integer> update(FishSlotMachine fishSlotMachine);

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
	public MsgDto<FishSlotMachine> getFishSlotMachine(String id);

	/**
	 * 通过随机值获取鱼类信息
	 * 
	 * @param randomValue
	 * @return
	 */
	public FishSlotMachine getFishSlotMachineRandomValue(Integer randomValue);

	/**
	 * 获取配置表
	 * 
	 * @param type
	 * @return
	 */
	public List<FishSlotMachine> getFishSlotMachineType(Integer type);

	/**
	 * 通过配置id查询鱼类配置信息
	 * 
	 * @param deployId
	 * @return
	 */
	public List<FishSlotMachine> findFishSlotMachineList(String deployId);

}
