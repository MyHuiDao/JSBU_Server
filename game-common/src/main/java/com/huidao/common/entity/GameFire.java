package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 游戏炮权重
 */
@Entity("game_fire")
public class GameFire implements Serializable {
	
	public GameFire() {
		
	}
	public GameFire(Integer level,Double power) {
		this.level=level;
		this.power=power;
	}
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 级别
	 **/
	@Column
	private Integer level;
	/**
	 * 权重
	 **/
	@Column
	private Double power;

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
	 * 级别
	 **/
	public Integer getLevel() {
		return this.level;
	}

	/**
	 * 级别
	 **/
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 权重
	 **/
	public Double getPower() {
		return this.power;
	}

	/**
	 * 权重
	 **/
	public void setPower(Double power) {
		this.power = power;
	}
}
