package com.huidao.admin.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.SysPermission;
import com.huidao.common.interfaces.admin.IPermissionService;
import com.huidao.common.msg.MsgDto;

@Controller
@RequestMapping("/auto")
public class AuthorityController {

	@Reference
	private IPermissionService permissionService;

	/**
	 * 以树结构显示权限相关信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAuthorityList")
	@Permission("system_authority")
	@JSON
	public MsgDto<List<SysPermission>> getAuthorityList() {
		return permissionService.findAll();
	}

	/**
	 * 通过权限id获取权限信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getAuthority", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<SysPermission> getAuthority(String pkId) {
		return permissionService.get(pkId);
	}

	/**
	 * 新增权限
	 * 
	 * @param sysPermission
	 * @return
	 */
	@RequestMapping(value = "/saveAuthority", method = RequestMethod.POST)
	@Permission("authority_new")
	@JSON
	public MsgDto<String> saveAuthority(SysPermission sysPermission) {
		return permissionService.add(sysPermission);
	}

	/**
	 * 修改权限
	 * 
	 * @param sysPermission
	 * @return
	 */
	@RequestMapping(value = "/updateAuthority", method = RequestMethod.POST)
	@Permission("authority_update")
	@JSON
	public MsgDto<String> updateAuthority(SysPermission sysPermission) {

		return permissionService.update(sysPermission);
	}

	/**
	 * 删除权限
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteAuthority", method = RequestMethod.POST)
	@Permission("authority_delete")
	@JSON
	public MsgDto<String> deleteAuthority(String id) {

		return permissionService.delete(id);
	}

}
