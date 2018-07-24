package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 鱼类倍率配置表
 */
@Entity("fish_magnification_deploy")
public class FishMagnificationDeploy implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * id
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 鱼名称
	 **/
	@Column
	private String fishName;
	/**
	 * 倍数
	 **/
	@Column
	private Integer multiple;
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
	public Integer getMultiple() {
		return this.multiple;
	}

	/**
	 * 倍数
	 **/
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
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
