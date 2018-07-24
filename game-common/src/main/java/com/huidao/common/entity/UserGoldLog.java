package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import java.util.Date;

import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 用户金币日志
 */
@Entity("user_gold_log")
public class UserGoldLog implements Serializable {
	public UserGoldLog() {
		
	}
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 0:增加，1：减少
	 **/
	@Column
	private Integer type;
	/**
	 * 来源(0:充值,1:赠送,3:游戏消耗)
	 **/
	@Column
	private Integer sourceType;
	/**
	 * 描述
	 **/
	@Column
	private String desc;
	/**
	 * 金币数量
	 **/
	@Column
	private Long goldNum;
	/**
	 * 添加之前金币数
	 **/
	@Column
	private Long goldAgo;
	/**
	 * 添加之后金币数
	 **/
	@Column
	private Long goldAfter;
	/**
	 * 游戏用户
	 */
	@Column
	private String userId;
	
	/**
	 * 创建时间
	 */
	@Column
	private Date createDate;
	
	
	/**
	 * 游戏类型
	 */
	@Column
	private String gameType;
	
	
	/**
	 * 操作人
	 */
	@Column
	private String sysUserId;

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
	 * 0:增加，1：减少
	 **/
	public Integer getType() {
		return this.type;
	}

	/**
	 * 0:增加，1：减少
	 **/
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 来源(0:充值,1:赠送,3:游戏消耗)
	 **/
	public Integer getSourceType() {
		return this.sourceType;
	}

	/**
	 * 来源(0:充值,1:赠送,3:游戏消耗)
	 **/
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	/**
	 * 描述
	 **/
	public String getDesc() {
		return this.desc;
	}

	/**
	 * 描述
	 **/
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 金币数量
	 **/
	public Long getGoldNum() {
		return this.goldNum;
	}

	/**
	 * 金币数量
	 **/
	public void setGoldNum(Long goldNum) {
		this.goldNum = goldNum;
	}

	/**
	 * 添加之前金币数
	 **/
	public Long getGoldAgo() {
		return this.goldAgo;
	}

	/**
	 * 添加之前金币数
	 **/
	public void setGoldAgo(Long goldAgo) {
		this.goldAgo = goldAgo;
	}

	/**
	 * 添加之后金币数
	 **/
	public Long getGoldAfter() {
		return this.goldAfter;
	}

	/**
	 * 添加之后金币数
	 **/
	public void setGoldAfter(Long goldAfter) {
		this.goldAfter = goldAfter;
	}

	
	/**
	 * 用户id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	/**
	 * 操作人
	 */
	public String getSysUserId() {
		return sysUserId;
	}

	/**
	 * 操作人
	 */
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}
	
	
	
	
}
