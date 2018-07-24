package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

@Entity("fishing_rule")
public class FishingRule implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 最大金币
	 **/
	@Column
	private Long maxGold;
	/**
	 * 最小金币
	 **/
	@Column
	private Long minGold;
	/**
	 * 赢的概率100
	 **/
	@Column
	private Double winNum;
	/**
	 * 类型：0:公共规则,1:私人规则,2:房间规则
	 **/
	@Column
	private Integer type;
	/**
	 * 规则名称
	 **/
	@Column
	private String name;
	
	/**
	 * 房间类型
	 */
	@Column
	private String roomType;

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
	 * 最大金币
	 **/
	public Long getMaxGold() {
		return this.maxGold;
	}

	/**
	 * 最大金币
	 **/
	public void setMaxGold(Long maxGold) {
		this.maxGold = maxGold;
	}

	/**
	 * 最小金币
	 **/
	public Long getMinGold() {
		return this.minGold;
	}

	/**
	 * 最小金币
	 **/
	public void setMinGold(Long minGold) {
		this.minGold = minGold;
	}

	/**
	 * 赢的概率100
	 **/
	public Double getWinNum() {
		return this.winNum;
	}

	/**
	 * 赢的概率100
	 **/
	public void setWinNum(Double winNum) {
		this.winNum = winNum;
	}

	/**
	 * 类型：0:公共规则,1:私人规则
	 **/
	public Integer getType() {
		return this.type;
	}

	/**
	 * 类型：0:公共规则,1:私人规则
	 **/
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 规则名称
	 **/
	public String getName() {
		return this.name;
	}

	/**
	 * 规则名称
	 **/
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * 房间类型
	 */
	public String getRoomType() {
		return roomType;
	}

	/**
	 * 房间类型
	 */
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	
}