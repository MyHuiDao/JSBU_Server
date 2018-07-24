package com.huidao.common.interfaces.admin;

import java.util.List;
import java.util.Map;

import com.huidao.common.entity.SysRole;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

/**
 * 角色service
 * @author Administrator
 *
 */
public interface IRoleService {
	
	
	
	/**
	 * 检查角色名称是否存在
	 * @param name
	 * @return
	 */
	public MsgDto<String> checkName(String id,String name);
	/**
	 * 新加角色
	 * @param role
	 * @return
	 */
	public MsgDto<String> add(SysRole role) ;
	
	/**
	 * 角色删除
	 * @param id
	 * @return
	 */
	public MsgDto<String> delete(String id);
	
	/**
	 * 角色更新
	 * @param role
	 * @return
	 */
	public MsgDto<String> update(SysRole role);
	
	
	/**
	 * 根据id 查询
	 * @param id
	 * @return
	 */
	public MsgDto<SysRole> get(String id);
	
	
	/**
	 * 权限配置
	 * @param roleId
	 * @param permissions
	 * @return
	 */
	public MsgDto<String> setRolePermissions(String roleId,List<String> permissions);
	
	
	/**
	 * 分页查询
	 * @param page
	 * @param size
	 * @param name
	 * @return
	 */
	public MsgDto<Page<SysRole>> findPage(Integer page,Integer size, Map<String, Object> parms);
}
