package com.huidao.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.yehebl.orm.anno.Dto;

/**
 * 下级代理实体类
 * 
 * @author lenovo
 *
 */
@Dto
public class Subagent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 下级代理昵称
	 */
	private String nickName;

	/**
	 * 总消费金额
	 */
	private BigDecimal totalAmount;

	/**
	 * 分红比例
	 */
	private Integer proportion;

	/**
	 * 奖金
	 */
	private BigDecimal bouns;

	/**
	 * 创建时间
	 */
	private Date createDate;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getProportion() {
		return proportion;
	}

	public void setProportion(Integer proportion) {
		this.proportion = proportion;
	}

	public BigDecimal getBouns() {
		return bouns;
	}

	public void setBouns(BigDecimal bouns) {
		this.bouns = bouns;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
