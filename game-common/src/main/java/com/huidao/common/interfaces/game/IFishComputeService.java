package com.huidao.common.interfaces.game;

import com.huidao.common.entity.FishBaseProperty;
import com.huidao.common.entity.FishControllerProperty;

/**
 * 捕鱼基础属性查询
 * 
 * @author Administrator
 *
 */
public interface IFishComputeService {
	/**
	 * 获取游戏基础属性，需要做缓存
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	public FishBaseProperty getFishBaseProperty(String areaId, String userId);

	/**
	 * 获取可控属性，需要做缓存
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	public FishControllerProperty getFishControllerProperty(String areaId, String userId);
}
