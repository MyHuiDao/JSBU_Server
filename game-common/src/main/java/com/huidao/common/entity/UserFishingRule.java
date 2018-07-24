package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

@Entity("user_fishing_rule")
public class UserFishingRule implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 捕鱼规则表
	 **/
	@Column
	private String fishingRuleId;
	/**
	 * 用户id
	 **/
	@Column
	private String userId;
	/**
	 * 状态：0:启用,1:禁用
	 **/
	@Column
	private Integer status;
	/**
	 * 规则名称
	 * 
	 * @param name
	 */
	private String name;

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
	 * 捕鱼规则表
	 **/
	public String getFishingRuleId() {
		return this.fishingRuleId;
	}

	/**
	 * 捕鱼规则表
	 **/
	public void setFishingRuleId(String fishingRuleId) {
		this.fishingRuleId = fishingRuleId;
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
	 * 状态：0:启用,1:禁用
	 **/
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 状态：0:启用,1:禁用
	 **/
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 规则名称
	 * 
	 * @param name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 规则名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
