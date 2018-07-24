package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 保险柜
 */
@Entity("safe_gold")
public class SafeGold implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 金币数
	 **/
	@Column
	private Integer gold;
	/**
	 * 用户id
	 **/
	@Column
	private String userId;
	/**
	 * 密码
	 **/
	@Column
	private String password;

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
	 * 金币数
	 **/
	public Integer getGold() {
		return this.gold;
	}

	/**
	 * 金币数
	 **/
	public void setGold(Integer gold) {
		this.gold = gold;
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
}
