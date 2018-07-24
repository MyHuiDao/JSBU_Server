package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 用户分享表
 */
@Entity("user_share")
public class UserShare implements Serializable {
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
	 * 创建时间
	 **/
	@Column
	private Date createdate;

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
	 * 创建时间
	 **/
	public Date getCreatedate() {
		return this.createdate;
	}

	/**
	 * 创建时间
	 **/
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
}
