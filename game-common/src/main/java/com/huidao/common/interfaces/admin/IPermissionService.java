package com.huidao.common.interfaces.admin;

import java.util.List;

import com.huidao.common.entity.SysPermission;
import com.huidao.common.msg.MsgDto;

/**
 * 权限查询
 * 
 * @author Administrator
 *
 */

public interface IPermissionService {
	/**
	 * 查询所有权限
	 * 
	 * @return
	 */
	public MsgDto<List<SysPermission>> findAll();

	/**
	 * 编辑
	 * 
	 * @return
	 */
	public MsgDto<String> update(SysPermission sp);

	/**
	 * 新增权限
	 * 
	 * @param sp
	 * @return
	 */
	public MsgDto<String> add(SysPermission sp);

	/**
	 * 删除权限
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<String> delete(String id);

	/**
	 * 获取单个权限
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<SysPermission> get(String id);

	/**
	 * 通过用户id获取权限code
	 * 
	 * @param userId
	 * @return
	 */
	public List<SysPermission> getPermissionCode(String userId);

	/**
	 * 获取权限列表
	 * 
	 * @param roleId
	 *            角色id
	 * @return
	 */
	public MsgDto<List<SysPermission>> getPermissionCodeByRoleId(String roleId);

}
