package com.huidao.common.enums;

public enum GoldSourceType {

	/**
	 * 充值
	 */
	pay(0,"充值"),
	
	free(1,"赠送"),
	
	game(2,"游戏消耗"),
	
	buy(3,"购买商品"),
	
	win(4,"赢的金币"),
	
	lose(5,"输的金币"),
	
	getCash(6,"提现的金币")
	
	
	;
	
	private GoldSourceType(int type,String desc) {
		this.type=type;
		this.desc=desc;
	}
	private int type;
	
	private String desc;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
