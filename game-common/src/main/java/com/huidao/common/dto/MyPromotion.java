package com.huidao.common.dto;

import java.io.Serializable;

import com.yehebl.orm.anno.Dto;

@Dto
public class MyPromotion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private String id;

	/**
	 * 用户token
	 */
	private String token;

	/**
	 * 用户编码
	 */
	private String code;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 分红人昵称
	 */
	private String bonusNickName;

	/**
	 * 提成总额
	 */
	private String money;

	/**
	 * 消费金额
	 */
	private String price;

	/**
	 * 分成比例
	 */
	private String bonusProportion;

	/**
	 * 获得的分红
	 */
	private String bonusMoney;

	/**
	 * 注册时间
	 */
	private String createTime;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBonusProportion() {
		return bonusProportion;
	}

	public void setBonusProportion(String bonusProportion) {
		this.bonusProportion = bonusProportion;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getBonusMoney() {
		return bonusMoney;
	}

	public void setBonusMoney(String bonusMoney) {
		this.bonusMoney = bonusMoney;
	}

	public String getBonusNickName() {
		return bonusNickName;
	}

	public void setBonusNickName(String bonusNickName) {
		this.bonusNickName = bonusNickName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
