package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.math.BigDecimal;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 人民币兑换金币
 */
@Entity("game_gold_exchange")
public class GameGoldExchange implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 排序
	 **/
	@Column
	private Integer seq;
	/**
	 * 人民币
	 **/
	@Column
	private BigDecimal rmb;
	/**
	 * 金币
	 **/
	@Column
	private Integer gold;
	/**
	 * 赠送金币
	 **/
	@Column
	private Integer freeGold;
	/**
	 * 图片资源
	 **/
	@Column
	private String img;
	
	/**
	 * 0:微信支付宝支付，1：苹果支付
	 */
	@Column
	private Integer type;

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
	 * 排序
	 **/
	public Integer getSeq() {
		return this.seq;
	}

	/**
	 * 排序
	 **/
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	/**
	 * 人民币
	 **/
	public BigDecimal getRmb() {
		return this.rmb;
	}

	/**
	 * 人民币
	 **/
	public void setRmb(BigDecimal rmb) {
		this.rmb = rmb;
	}

	/**
	 * 金币
	 **/
	public Integer getGold() {
		return this.gold;
	}

	/**
	 * 金币
	 **/
	public void setGold(Integer gold) {
		this.gold = gold;
	}

	/**
	 * 赠送金币
	 **/
	public Integer getFreeGold() {
		return this.freeGold;
	}

	/**
	 * 赠送金币
	 **/
	public void setFreeGold(Integer freeGold) {
		this.freeGold = freeGold;
	}

	/**
	 * 图片资源
	 **/
	public String getImg() {
		return this.img;
	}

	/**
	 * 图片资源
	 **/
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * 0:微信支付宝支付，1：苹果支付
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * 0:微信支付宝支付，1：苹果支付
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
