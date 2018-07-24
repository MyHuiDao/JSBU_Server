package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

@Entity("match_fish_open_prize")
public class MatchFishOpenPrize implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 对应的房间id
	 **/
	@Column
	private String roomId;
	/**
	 * 开奖期数(自增)
	 **/
	@Column
	private Integer count;
	/**
	 * 开奖结果
	 **/
	@Column
	private String result;
	/**
	 * 创建时间
	 **/
	@Column
	private Date createDate;
	/**
	 * 开奖时间
	 **/
	@Column
	private Date openDate;
	/**
	 * 状态0：未开奖，1：开奖中，2：已开奖
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
	 * 对应的房间id
	 **/
	public String getRoomId() {
		return this.roomId;
	}

	/**
	 * 对应的房间id
	 **/
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	/**
	 * 开奖期数(自增)
	 **/
	public Integer getCount() {
		return this.count;
	}

	/**
	 * 开奖期数(自增)
	 **/
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 开奖结果
	 **/
	public String getResult() {
		return this.result;
	}

	/**
	 * 开奖结果
	 **/
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * 创建时间
	 **/
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 创建时间
	 **/
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 开奖时间
	 **/
	public Date getOpenDate() {
		return this.openDate;
	}

	/**
	 * 开奖时间
	 **/
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	/**
	 * 状态0：未开奖，1：开奖中，2：已开奖
	 **/
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 状态0：未开奖，1：开奖中，2：已开奖
	 **/
	public void setStatus(Integer status) {
		this.status = status;
	}
}
