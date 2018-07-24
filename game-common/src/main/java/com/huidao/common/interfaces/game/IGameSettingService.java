package com.huidao.common.interfaces.game;

import com.huidao.common.entity.GameSetting;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

/**
 * 游戏设置
 * 
 * @author Administrator
 *
 */
public interface IGameSettingService {
	/**
	 * 获取值
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key);

	/**
	 * 刷新值
	 * 
	 * @param value
	 * @return
	 */
	public void refresh(String key);

	/**
	 * 分页查询游戏设置
	 * 
	 * @param key
	 * @return
	 */
	public MsgDto<Page<GameSetting>> findPage(Integer page,Integer size,String key);

	/**
	 * 获取单个游戏设置对象
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<GameSetting> getSettingById(String id);

	/**
	 * 游戏设置更新
	 * 
	 * @param gameSetting
	 * @return
	 */
	public MsgDto<String> update(String sysUserAccount, GameSetting gameSetting);

	/**
	 * 通过key获取gameSetting
	 * 
	 * @param key
	 * @return
	 */
	public GameSetting getKeyGameSetting(String key);

}