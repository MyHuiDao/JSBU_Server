package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

@Entity("fish_odds")
public class FishOdds implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 收支比起点
	 **/
	@Column
	private Double shouzhibiKs;
	/**
	 * 收支比结束
	 **/
	@Column
	private Double shouzhibiJs;
	/**
	 * 赔率
	 **/
	@Column
	private Double odds;

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
	 * 收支比起点
	 **/
	public Double getShouzhibiKs() {
		return this.shouzhibiKs;
	}

	/**
	 * 收支比起点
	 **/
	public void setShouzhibiKs(Double shouzhibiKs) {
		this.shouzhibiKs = shouzhibiKs;
	}

	/**
	 * 收支比结束
	 **/
	public Double getShouzhibiJs() {
		return this.shouzhibiJs;
	}

	/**
	 * 收支比结束
	 **/
	public void setShouzhibiJs(Double shouzhibiJs) {
		this.shouzhibiJs = shouzhibiJs;
	}

	/**
	 * 赔率
	 **/
	public Double getOdds() {
		return this.odds;
	}

	/**
	 * 赔率
	 **/
	public void setOdds(Double odds) {
		this.odds = odds;
	}
}
