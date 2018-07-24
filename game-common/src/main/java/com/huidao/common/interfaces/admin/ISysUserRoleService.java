package com.huidao.common.interfaces.admin;

import java.util.List;

import com.huidao.common.entity.SysUserRole;
import com.huidao.common.msg.MsgDto;

public interface ISysUserRoleService {

	/**
	 * 保存角色id和用户id
	 * 
	 * @param sysUserRole
	 * @return
	 */
	public MsgDto<String> add(SysUserRole sysUserRole);

	/**
	 * 查询用户角色信息
	 * 
	 * @param sysUserId
	 *            用户id
	 * @return
	 */
	public List<SysUserRole> getSysUserRole(String sysUserId);

	/**
	 * 删除用户角色信息
	 * 
	 * @param sysUserId
	 * @return
	 */
	public MsgDto<String> delete(String id);

}
