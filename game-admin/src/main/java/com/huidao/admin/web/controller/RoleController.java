package com.huidao.admin.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.SysPermission;
import com.huidao.common.entity.SysRole;
import com.huidao.common.interfaces.admin.IPermissionService;
import com.huidao.common.interfaces.admin.IRoleService;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

@Controller
@RequestMapping("/role")
public class RoleController {
	@Reference
	private IRoleService roleService;

	@Reference
	private IPermissionService permissionService;

	/**
	 * 查询角色名称
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/getRoleName", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<Page<SysRole>> getRoleName(String name) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotBlank(name)) {
			map.put("LIKE_name", name);
		}
		return roleService.findPage(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(), map);
	}

	/**
	 * 通过id查询
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getRoleById", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<SysRole> getRoleById(String id) {
		return roleService.get(id);
	}

	/**
	 * 检查角色名称是否存在
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/checkName", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<String> checkName(String id, String name) {
		return roleService.checkName(id, name);
	}

	/**
	 * 通过id删除角色名称
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delRoleByName", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<String> delRoleByName(String id) {
		return roleService.delete(id);
	}

	/**
	 * 添加角色名称
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/addRoleByName", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<String> addRoleByName(SysRole role) {
		return roleService.add(role);
	}

	/**
	 * 修改角色名称
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/updRoleByName", method = RequestMethod.POST)
	@Permission
	@JSON
	public MsgDto<String> updRoleByName(SysRole role) {
		return roleService.update(role);
	}

	/**
	 * 权限配置
	 * 
	 * @param roleId
	 * @param permissions
	 * @return
	 */
	@RequestMapping(value = "/setRolePermissions", method = RequestMethod.POST)
	@Permission
	@JSON
	public MsgDto<String> setRolePermissions(String roleId, String permissions) {
		if (StringUtils.isBlank(permissions)) {
			return roleService.setRolePermissions(roleId, null);
		}
		String[] split = permissions.split(",");
		List<String> list = new ArrayList<>();
		for (int i = 0; i < split.length; i++) {
			list.add(split[i]);
		}
		return roleService.setRolePermissions(roleId, list);
	}

	/**
	 * 获取权限列表
	 * 
	 * @param roleId
	 *            角色id
	 * @return
	 */
	@RequestMapping(value = "/getPermissionCodeByRoleId", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<List<SysPermission>> getPermissionCodeByRoleId(String roleId) {
		return permissionService.getPermissionCodeByRoleId(roleId);
	}
}
