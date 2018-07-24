package com.huidao.common.interfaces.game;

import java.util.List;

import com.huidao.common.entity.GameArea;
import com.huidao.common.msg.MsgDto;

/**
 * 获取游戏区
 * @author Administrator
 *
 */
public interface IGameAreaService {
	/**
	 * 获取游戏区
	 * @param id
	 * @return
	 */
	 public GameArea get(String id);

	 
	 /**
	  * 用类型查询游戏区域
	  * @param type
	  * @return
	  */
	public MsgDto<List<GameArea>> findGameAreaType(String type);
}
