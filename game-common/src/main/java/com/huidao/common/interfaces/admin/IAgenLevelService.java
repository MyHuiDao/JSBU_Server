package com.huidao.common.interfaces.admin;

import java.util.List;

import com.huidao.common.entity.AgenLevel;
import com.huidao.common.msg.MsgDto;

/**
 * 代理级别设置
 * 
 * @author lenovo
 *
 */
public interface IAgenLevelService {
	/**
	 * 显示所有代理级别
	 * 
	 * @return
	 */
	public List<AgenLevel> findAgenLevelAll();

	/**
	 * 添加代理级别
	 * 
	 * @param agenLevel
	 * @return
	 */
	public MsgDto<String> add(AgenLevel agenLevel);

	/**
	 * 修改代理级别
	 * 
	 * @param agenLevel
	 * @return
	 */
	public MsgDto<String> update(AgenLevel agenLevel);

	/**
	 * 删除代理级别
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<String> delete(String id);

	/**
	 * 获取代理级别
	 * 
	 * @param id
	 * @return
	 */
	public AgenLevel get(String id);

	
	/**
	 * 获取下一级别代理
	 * @param level
	 * @return
	 */
	public AgenLevel getNextLevel(Integer level);

}
