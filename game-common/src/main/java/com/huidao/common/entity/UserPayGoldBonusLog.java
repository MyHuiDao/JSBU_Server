package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 用户充值金币被分红记录
 */
@Entity("user_pay_gold_bonus_log")
public class UserPayGoldBonusLog implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 金币
	 **/
	@Column
	private Integer gold;
	/**
	 * 分红人
	 **/
	@Column
	private String sysUserId;
	/**
	 * 分红时间
	 **/
	@Column
	private Date createDate;
	/**
	 * 分红来源用户
	 **/
	@Column
	private String userId;

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
	 * 金币
	 **/
	public Integer getGold() {
		return this.gold;
	}

	/**
	 * 金币
	 **/
	public void setGold(Integer gold) {
		this.gold = gold;
	}

	/**
	 * 分红人
	 **/
	public String getSysUserId() {
		return this.sysUserId;
	}

	/**
	 * 分红人
	 **/
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	/**
	 * 分红时间
	 **/
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 分红时间
	 **/
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 分红来源用户
	 **/
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 分红来源用户
	 **/
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
