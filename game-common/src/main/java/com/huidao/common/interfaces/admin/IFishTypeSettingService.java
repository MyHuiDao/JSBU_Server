package com.huidao.common.interfaces.admin;

import java.util.List;

import com.huidao.common.entity.FishTypeSetting;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

public interface IFishTypeSettingService {

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public MsgDto<Page<FishTypeSetting>> findFishTypeSettingAll(Integer level,Integer page, Integer size);

	/**
	 * 增
	 * 
	 * @param fishTypeSetting
	 * @return
	 */
	public MsgDto<Integer> add(FishTypeSetting fishTypeSetting);

	/**
	 * 删
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<Integer> delete(String id);

	/**
	 * 改
	 * 
	 * @param fishTypeSetting
	 * @return
	 */
	public MsgDto<Integer> update(FishTypeSetting fishTypeSetting);

	/**
	 * 查
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<FishTypeSetting> getFishTypeSetting(String id);
	
	/**
	 * 按级别查询列表，需要写缓存
	 * @return
	 */
	public List<FishTypeSetting> getFishTypeSetting();

}
