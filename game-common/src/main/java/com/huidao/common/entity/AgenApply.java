package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

@Entity("agen_apply")
public class AgenApply implements Serializable {
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
	private String gameUserId;
	/**
	 * 账号
	 **/
	@Column
	private String account;
	/**
	 * 密码
	 **/
	@Column
	private String password;
	/**
	 * 盐
	 **/
	@Column
	private String salt;
	/**
	 * 申请时间
	 **/
	@Column
	private Date applyDate;
	/**
	 * 审核时间
	 **/
	@Column
	private Date auditDate;
	/**
	 * 审核状态(0:未审核,1:审核通过,2:审核失败)
	 **/
	@Column
	private Integer auditStatus;

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
	public String getGameUserId() {
		return this.gameUserId;
	}

	/**
	 * 游戏用户id
	 **/
	public void setGameUserId(String gameUserId) {
		this.gameUserId = gameUserId;
	}

	/**
	 * 账号
	 **/
	public String getAccount() {
		return this.account;
	}

	/**
	 * 账号
	 **/
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 密码
	 **/
	public String getPassword() {
		return this.password;
	}

	/**
	 * 密码
	 **/
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 盐
	 **/
	public String getSalt() {
		return this.salt;
	}

	/**
	 * 盐
	 **/
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * 申请时间
	 **/
	public Date getApplyDate() {
		return this.applyDate;
	}

	/**
	 * 申请时间
	 **/
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * 审核时间
	 **/
	public Date getAuditDate() {
		return this.auditDate;
	}

	/**
	 * 审核时间
	 **/
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	/**
	 * 审核状态(0:未审核,1:审核通过,2:审核失败)
	 **/
	public Integer getAuditStatus() {
		return this.auditStatus;
	}

	/**
	 * 审核状态(0:未审核,1:审核通过,2:审核失败)
	 **/
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
}
