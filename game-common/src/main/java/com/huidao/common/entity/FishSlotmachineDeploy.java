package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 鱼类老虎机配置表
 */
@Entity("fish_slotmachine_deploy")
public class FishSlotmachineDeploy implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * id
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 配置名称
	 **/
	@Column
	private String name;
	/**
	 * 配置类型
	 **/
	@Column
	private Integer type;
	/**
	 * 创建时间
	 **/
	@Column
	private Date createDate;
	/**
	 * 收支比
	 **/
	@Column
	private Double incomeRatio;

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
	 * 配置名称
	 **/
	public String getName() {
		return this.name;
	}

	/**
	 * 配置名称
	 **/
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 配置类型
	 **/
	public Integer getType() {
		return this.type;
	}

	/**
	 * 配置类型
	 **/
	public void setType(Integer type) {
		this.type = type;
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
	 * 收支比
	 **/
	public Double getIncomeRatio() {
		return this.incomeRatio;
	}

	/**
	 * 收支比
	 **/
	public void setIncomeRatio(Double incomeRatio) {
		this.incomeRatio = incomeRatio;
	}
}
