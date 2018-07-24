package com.huidao.common.dto;

import java.math.BigDecimal;

import com.yehebl.orm.anno.Dto;

/**
 * 收支比
 * @author Administrator
 *
 */
@Dto
public class GoldTypeSumDto {
	/**
	 * 类型 0：增加 1减少
	 */
	private Integer type;
	
	/**
	 * 获取金币
	 */
	private BigDecimal gold;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public BigDecimal getGold() {
		return gold;
	}

	public void setGold(BigDecimal gold) {
		this.gold = gold;
	}

	
	
	
	
	
	
}
