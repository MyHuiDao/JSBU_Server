package com.huidao.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.yehebl.orm.anno.Column;
import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 游戏系统设置类
 */
@Entity("game_setting")
public class GameSetting implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 键
	 **/
	@Column
	private String key;
	/**
	 * 值
	 **/
	@Column
	private String value;
	/**
	 * 描述
	 **/
	@Column
	private String desc;
	/**
	 * 更新时间
	 **/
	@Column
	private Date updateTime;
	/**
	 * 更新人
	 **/
	@Column
	private String adminUserId;

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
	 * 键
	 **/
	public String getKey() {
		return this.key;
	}

	/**
	 * 键
	 **/
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 值
	 **/
	public String getValue() {
		return this.value;
	}

	/**
	 * 值
	 **/
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 描述
	 **/
	public String getDesc() {
		return this.desc;
	}

	/**
	 * 描述
	 **/
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 更新时间
	 **/
	public Date getUpdateTime() {
		return this.updateTime;
	}

	/**
	 * 更新时间
	 **/
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 更新人
	 **/
	public String getAdminUserId() {
		return this.adminUserId;
	}

	/**
	 * 更新人
	 **/
	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}
}
