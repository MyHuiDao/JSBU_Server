package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 用户首次操作
 */
@Entity("game_user_once")
public class GameUserOnce implements Serializable {
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
	 * 类型
	 **/
	@Column
	private Integer type;

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
	 * 类型
	 **/
	public Integer getType() {
		return this.type;
	}

	/**
	 * 类型
	 **/
	public void setType(Integer type) {
		this.type = type;
	}
}
