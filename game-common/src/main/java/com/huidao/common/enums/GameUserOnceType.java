package com.huidao.common.enums;

public enum GameUserOnceType {
	
	/**
	 * 首次支付
	 */
	once_pay(0),

	/**
	 * 首提
	 */
	once_get_cash(1);
	
	private GameUserOnceType(int type) {
		this.type=type;
	}
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
