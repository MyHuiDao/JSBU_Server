package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 游戏区域规则关联表
 */
@Entity("game_area_rule")
public class GameAreaRule implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 规则id
	 **/
	@Column
	private String ruleId;
	/**
	 * 区域id
	 **/
	@Column
	private String areaId;

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
	 * 规则id
	 **/
	public String getRuleId() {
		return this.ruleId;
	}

	/**
	 * 规则id
	 **/
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * 区域id
	 **/
	public String getAreaId() {
		return this.areaId;
	}

	/**
	 * 区域id
	 **/
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
}
