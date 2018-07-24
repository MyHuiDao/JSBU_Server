package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

@Entity("sys_role_permission")
public class SysRolePermission implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 角色id
	 **/
	@Column
	private String roleId;
	/**
	 * 权限id
	 **/
	@Column
	private String permissionId;

	/**
	 * 主键
	 **/
	public String getId() {
		return this.id;
	}

	/**
	 * 主键
	 **/
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 角色id
	 **/
	public String getRoleId() {
		return this.roleId;
	}

	/**
	 * 角色id
	 **/
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * 权限id
	 **/
	public String getPermissionId() {
		return this.permissionId;
	}

	/**
	 * 权限id
	 **/
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
}
