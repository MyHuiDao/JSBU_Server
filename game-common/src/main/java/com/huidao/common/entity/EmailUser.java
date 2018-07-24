package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

@Entity("email_user")
public class EmailUser implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 游戏用户id
	 **/
	@Column
	private String userId;
	/**
	 * 邮件id
	 **/
	@Column
	private String emailId;
	/**
	 * 阅读时间
	 **/
	@Column
	private Date readTime;
	/**
	 * 状态(0:已阅读,1:未阅读,2:删除)
	 **/
	@Column
	private Integer status;

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
	 * 游戏用户id
	 **/
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 游戏用户id
	 **/
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 邮件id
	 **/
	public String getEmailId() {
		return this.emailId;
	}

	/**
	 * 邮件id
	 **/
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * 阅读时间
	 **/
	public Date getReadTime() {
		return this.readTime;
	}

	/**
	 * 阅读时间
	 **/
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	/**
	 * 状态(0:已阅读,1:未阅读,2:删除)
	 **/
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 状态(0:已阅读,1:未阅读,2:删除)
	 **/
	public void setStatus(Integer status) {
		this.status = status;
	}
}
