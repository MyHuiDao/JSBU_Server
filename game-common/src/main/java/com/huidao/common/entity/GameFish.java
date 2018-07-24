package com.huidao.common.entity;

import java.io.Serializable;

import com.yehebl.orm.anno.Column;
import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 鱼种类
 */
@Entity("game_fish")
public class GameFish implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 名称
	 **/
	@Column
	private String name;
	/**
	 * 类型
	 **/
	@Column
	private String type;
	/**
	 * 最小金币
	 **/
	@Column
	private Integer goldMin;
	/**
	 * 最大金币
	 **/
	@Column
	private Integer goldMax;
	/**
	 * 鱼权值
	 **/
	@Column
	private Integer power;
	/**
	 * 速度(逗号分隔)
	 **/
	@Column
	private String speed;
	/**
	 * 捕获比对参数
	 **/
	@Column
	private Double contrast;

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
	 * 名称
	 **/
	public String getName() {
		return this.name;
	}

	/**
	 * 名称
	 **/
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 类型
	 **/
	public String getType() {
		return this.type;
	}

	/**
	 * 类型
	 **/
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 最小金币
	 **/
	public Integer getGoldMin() {
		return this.goldMin;
	}

	/**
	 * 最小金币
	 **/
	public void setGoldMin(Integer goldMin) {
		this.goldMin = goldMin;
	}

	/**
	 * 最大金币
	 **/
	public Integer getGoldMax() {
		return this.goldMax;
	}

	/**
	 * 最大金币
	 **/
	public void setGoldMax(Integer goldMax) {
		this.goldMax = goldMax;
	}

	/**
	 * 鱼权值
	 **/
	public Integer getPower() {
		return this.power;
	}

	/**
	 * 鱼权值
	 **/
	public void setPower(Integer power) {
		this.power = power;
	}

	/**
	 * 速度(逗号分隔)
	 **/
	public String getSpeed() {
		return this.speed;
	}

	/**
	 * 速度(逗号分隔)
	 **/
	public void setSpeed(String speed) {
		this.speed = speed;
	}

	/**
	 * 捕获比对参数
	 **/
	public Double getContrast() {
		return this.contrast;
	}

	/**
	 * 捕获比对参数
	 **/
	public void setContrast(Double contrast) {
		this.contrast = contrast;
	}
}
