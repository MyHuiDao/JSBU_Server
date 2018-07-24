package com.huidao.common.entity;

import java.io.Serializable;

import com.yehebl.orm.anno.Column;
import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

@Entity("sys_user_role")
public class SysUserRole implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 用户id
	 **/
	@Column
	private String sysUserId;
	/**
	 * 角色id
	 **/
	@Column
	private String roleId;
	/**
	 * 角色名称
	 */
	private String name;

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
	 * 用户id
	 **/
	public String getSysUserId() {
		return this.sysUserId;
	}

	/**
	 * 用户id
	 **/
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
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
	 * 角色名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 角色名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}