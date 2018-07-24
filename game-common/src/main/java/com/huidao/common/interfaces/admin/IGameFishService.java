package com.huidao.common.interfaces.admin;

import java.util.List;

import com.huidao.common.entity.GameFish;
import com.huidao.common.msg.MsgDto;

/**
 * 鱼种类接口
 * 
 * @author lenovo
 *
 */
public interface IGameFishService {
	/**
	 * 查看鱼种类所有数据
	 * 
	 * @return
	 */
	public List<GameFish> findGameFishData();

	/**
	 * 添加鱼种类
	 * 
	 * @return
	 */
	public MsgDto<String> add(GameFish gameFish);

	/**
	 * 修改鱼种类
	 * 
	 * @param gameFish
	 * @return
	 */
	public MsgDto<String> update(GameFish gameFish);

	/**
	 * 删除鱼种类
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<String> delete(String id);

	/**
	 * 获取鱼种类
	 * 
	 * @param id
	 * @return
	 */
	public GameFish getGameFish(String id);

	/**
	 * 通过鱼种的名称查询是否有该鱼种
	 * 
	 * @param name
	 * @return
	 */
	public GameFish getNameGameFish(String name);

	/**
	 * 获取鱼群信息
	 * 
	 * @return
	 */
	public MsgDto<List<GameFish>> getFishInfoList();

	/**
	 * 通过类型获取鱼
	 * 
	 * @param type
	 * @return
	 */
	public GameFish getGameFishType(String type);

}
