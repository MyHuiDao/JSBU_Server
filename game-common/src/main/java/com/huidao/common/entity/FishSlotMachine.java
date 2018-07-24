package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 鱼儿老虎机鱼类表
 */
@Entity("fish_slot_machine")
public class FishSlotMachine implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * id
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 随机值
	 **/
	@Column
	private Integer randomValue;
	/**
	 * 鱼名称
	 **/
	@Column
	private String fishName;
	/**
	 * 倍数
	 **/
	@Column
	private String multipleId;
	/**
	 * 配置id
	 **/
	@Column
	private String deployId;
	/**
	 * 创建时间
	 **/
	@Column
	private Date createDate;

	/**
	 * id
	 **/
	public String getId() {
		return this.id;
	}

	/**
	 * id
	 **/
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 随机值
	 **/
	public Integer getRandomValue() {
		return this.randomValue;
	}

	/**
	 * 随机值
	 **/
	public void setRandomValue(Integer randomValue) {
		this.randomValue = randomValue;
	}

	/**
	 * 鱼名称
	 **/
	public String getFishName() {
		return this.fishName;
	}

	/**
	 * 鱼名称
	 **/
	public void setFishName(String fishName) {
		this.fishName = fishName;
	}

	/**
	 * 倍数
	 **/
	public String getMultipleId() {
		return this.multipleId;
	}

	/**
	 * 倍数
	 **/
	public void setMultipleId(String multipleId) {
		this.multipleId = multipleId;
	}

	/**
	 * 配置id
	 **/
	public String getDeployId() {
		return this.deployId;
	}

	/**
	 * 配置id
	 **/
	public void setDeployId(String deployId) {
		this.deployId = deployId;
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
}
