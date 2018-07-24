package com.huidao.common.entity;

import java.io.Serializable;

import com.yehebl.orm.anno.Column;
import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 权限
 */
@Entity("sys_permission")
public class SysPermission implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 权限名称
	 **/
	@Column
	private String name;
	/**
	 * 权限编码
	 **/
	@Column
	private String code;
	/**
	 * 父级权限
	 **/
	@Column
	private String parentId;
	/**
	 * 排序
	 **/
	@Column
	private Integer seq;

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
	 * 权限名称
	 **/
	public String getName() {
		return this.name;
	}

	/**
	 * 权限名称
	 **/
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 权限编码
	 **/
	public String getCode() {
		return this.code;
	}

	/**
	 * 权限编码
	 **/
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 父级权限
	 **/
	public String getParentId() {
		return this.parentId;
	}

	/**
	 * 父级权限
	 **/
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 排序
	 **/
	public Integer getSeq() {
		return this.seq;
	}

	/**
	 * 排序
	 **/
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
}