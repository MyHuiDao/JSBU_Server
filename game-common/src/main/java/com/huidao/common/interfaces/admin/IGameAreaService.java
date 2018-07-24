package com.huidao.common.interfaces.admin;

import java.util.List;

import com.huidao.common.entity.GameArea;
import com.huidao.common.entity.GameAreaRule;
import com.huidao.common.msg.MsgDto;

/**
 * 游戏区类别
 * 
 * @author lenovo
 *
 */
public interface IGameAreaService {
	/**
	 * 获取所有的游戏区
	 * 
	 * @return
	 */
	public MsgDto<List<GameArea>> findGameAreaAll();

	/**
	 * 添加
	 * 
	 * @param gameArea
	 * @return
	 */
	public MsgDto<Integer> add(GameArea gameArea);

	/**
	 * 修改
	 * 
	 * @param gameArea
	 * @return
	 */
	public MsgDto<Integer> update(GameArea gameArea);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<Integer> delete(String id);

	/**
	 * 获取游戏区类别
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<GameArea> get(String id);

	/**
	 * 获取相关规则
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<List<GameArea>> getFishingRule(String id);

	/**
	 * 将规则添加到关联表中
	 * 
	 * @param gameAreaRule
	 * @return
	 */
	public MsgDto<Integer> addFishingRule(GameAreaRule gameAreaRule);

	/**
	 * 删除关联表中的规则
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<Integer> deleteGameAreaRule(String id);

}
