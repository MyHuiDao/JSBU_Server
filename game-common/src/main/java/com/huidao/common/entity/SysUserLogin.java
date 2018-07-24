package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 用户登录日志
 */
@Entity("sys_user_login")
public class SysUserLogin implements Serializable {
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
	private String userId;
	/**
	 * ip
	 **/
	@Column
	private String ip;
	/**
	 * 登录时间
	 **/
	@Column
	private Date loginTime;

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
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 用户id
	 **/
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * ip
	 **/
	public String getIp() {
		return this.ip;
	}

	/**
	 * ip
	 **/
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 登录时间
	 **/
	public Date getLoginTime() {
		return this.loginTime;
	}

	/**
	 * 登录时间
	 **/
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

}
