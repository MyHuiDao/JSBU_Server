package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.math.BigDecimal;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 鱼儿快跑下注表
 */
@Entity("match_fish_bets")
public class MatchFishBets implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 开奖期数id
	 **/
	@Column
	private String openPrizeId;
	/**
	 * 游戏用户id
	 **/
	@Column
	private String userId;
	/**
	 * 下注号
	 **/
	@Column
	private String betsNumber;
	/**
	 * 下注金币
	 **/
	@Column
	private Integer betsGold;
	/**
	 * 下注时间
	 **/
	@Column
	private Date betsDate;
	/**
	 * 状态：0未开奖，1：未中奖，2：中奖
	 **/
	@Column
	private Integer status;
	/**
	 * 赔率
	 **/
	@Column
	private BigDecimal odds;
	/**
	 * 获取金币
	 **/
	@Column
	private Long getGold;

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
	 * 开奖期数id
	 **/
	public String getOpenPrizeId() {
		return this.openPrizeId;
	}

	/**
	 * 开奖期数id
	 **/
	public void setOpenPrizeId(String openPrizeId) {
		this.openPrizeId = openPrizeId;
	}

	/**
	 * 游戏用户id
	 **/
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 游戏用户id
	 **/
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 下注号
	 **/
	public String getBetsNumber() {
		return this.betsNumber;
	}

	/**
	 * 下注号
	 **/
	public void setBetsNumber(String betsNumber) {
		this.betsNumber = betsNumber;
	}

	/**
	 * 下注金币
	 **/
	public Integer getBetsGold() {
		return this.betsGold;
	}

	/**
	 * 下注金币
	 **/
	public void setBetsGold(Integer betsGold) {
		this.betsGold = betsGold;
	}

	/**
	 * 下注时间
	 **/
	public Date getBetsDate() {
		return this.betsDate;
	}

	/**
	 * 下注时间
	 **/
	public void setBetsDate(Date betsDate) {
		this.betsDate = betsDate;
	}

	/**
	 * 状态：0未开奖，1：未中奖，2：中奖
	 **/
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 状态：0未开奖，1：未中奖，2：中奖
	 **/
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 赔率
	 **/
	public BigDecimal getOdds() {
		return this.odds;
	}

	/**
	 * 赔率
	 **/
	public void setOdds(BigDecimal odds) {
		this.odds = odds;
	}

	/**
	 * 获取金币
	 **/
	public Long getGetGold() {
		return this.getGold;
	}

	/**
	 * 获取金币
	 **/
	public void setGetGold(Long getGold) {
		this.getGold = getGold;
	}
}
