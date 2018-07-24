package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

@Entity("fish_slot_machine_bet")
public class FishSlotMachineBet implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * id
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 游戏用户id
	 **/
	@Column
	private String userId;
	/**
	 * 用户下注
	 **/
	@Column
	private String userBet;
	/**
	 * 下注时间
	 **/
	@Column
	private Date betDate;
	/**
	 * 赔率
	 **/
	@Column
	private String odds;
	/**
	 * 获得金币
	 **/
	@Column
	private Integer getGoldCoins;
	/**
	 * 开奖结果
	 **/
	@Column
	private String lotteryResult;

	/**
	 * 配置表阶段
	 */
	@Column
	private Integer stage;

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
	 * 用户下注
	 **/
	public String getUserBet() {
		return this.userBet;
	}

	/**
	 * 用户下注
	 **/
	public void setUserBet(String userBet) {
		this.userBet = userBet;
	}

	/**
	 * 下注时间
	 **/
	public Date getBetDate() {
		return this.betDate;
	}

	/**
	 * 下注时间
	 **/
	public void setBetDate(Date betDate) {
		this.betDate = betDate;
	}

	/**
	 * 赔率
	 **/
	public String getOdds() {
		return this.odds;
	}

	/**
	 * 赔率
	 **/
	public void setOdds(String odds) {
		this.odds = odds;
	}

	/**
	 * 获得金币
	 **/
	public Integer getGetGoldCoins() {
		return this.getGoldCoins;
	}

	/**
	 * 获得金币
	 **/
	public void setGetGoldCoins(Integer getGoldCoins) {
		this.getGoldCoins = getGoldCoins;
	}

	/**
	 * 开奖结果
	 **/
	public String getLotteryResult() {
		return this.lotteryResult;
	}

	/**
	 * 开奖结果
	 **/
	public void setLotteryResult(String lotteryResult) {
		this.lotteryResult = lotteryResult;
	}

	/**
	 * 配置表阶段
	 */
	public Integer getStage() {
		return stage;
	}

	/**
	 * 配置表阶段
	 */
	public void setStage(Integer stage) {
		this.stage = stage;
	}

}
