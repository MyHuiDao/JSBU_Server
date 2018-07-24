package com.huidao.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 收支比
 * @author Administrator
 *
 */
public class GetOutGoldDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 成本金币
	 */
	private BigDecimal costGold=BigDecimal.ZERO;
	
	
	/**
	 * 当前金币数
	 */
	private BigDecimal currentGold=BigDecimal.ZERO;

	public BigDecimal getCostGold() {
		return costGold;
	}

	public void setCostGold(BigDecimal costGold) {
		this.costGold = costGold;
	}

	public BigDecimal getCurrentGold() {
		return currentGold;
	}

	public void setCurrentGold(BigDecimal currentGold) {
		this.currentGold = currentGold;
	}

	
	
	
	
	
	
}
