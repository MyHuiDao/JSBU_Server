package com.huidao.common.interfaces.game.notice;

import java.util.List;

import com.huidao.common.entity.GameNotice;
import com.huidao.common.msg.MsgDto;

/**
 * 游戏公告
 * 
 * @author Administrator
 *
 */
public interface IGameNoticeService {

	/**
	 * 根据类型获取公告
	 * 
	 * @param type
	 * @return
	 */
	public MsgDto<List<GameNotice>> findByType(String type);

	/**
	 * 获取所有的游戏公告
	 * 
	 * @param type
	 * @return
	 */
	public MsgDto<List<GameNotice>> findGameNoticeAll(String type);

	/**
	 * 添加
	 * 
	 * @param gameNotice
	 * @return
	 */
	public MsgDto<Integer> add(GameNotice gameNotice);

	/**
	 * 修改
	 * 
	 * @param gameNotice
	 * @return
	 */
	public MsgDto<Integer> update(GameNotice gameNotice);

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
	public MsgDto<GameNotice> get(String id);

}
