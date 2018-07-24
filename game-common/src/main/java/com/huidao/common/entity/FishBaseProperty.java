package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 捕鱼公式基本属性
 */
@Entity("fish_base_property")
public class FishBaseProperty implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 炮弹基础消耗
	 **/
	@Column
	private Double fireConsume;
	/**
	 * 炮弹攻击力
	 **/
	@Column
	private Double fireAttack;
	/**
	 * 整体控制基数
	 **/
	@Column
	private Double controllerBodyBaseNum;
	/**
	 * 玩家基数
	 **/
	@Column
	private Double controllerPlayerBaseNum;
	/**
	 * 幅度基数
	 **/
	@Column
	private Double controllerFuDuBaseNum;
	/**
	 * 捕获基数
	 **/
	@Column
	private Double controllerCatchBaseNum;
	/**
	 * 鱼基础生命值
	 **/
	@Column
	private Double fishBaseHpNum;
	/**
	 * 鱼生命系数
	 **/
	@Column
	private Double fishHpCoefficient;
	/**
	 * 鱼生命基数
	 **/
	@Column
	private Double fishBaseHp;
	/**
	 * 得分基数
	 **/
	@Column
	private Double goldBaseNum;
	/**
	 * 得分系数
	 **/
	@Column
	private Double goldCoefficient;
	/**
	 * 类型(0:公共,1:区域,2:个人)
	 **/
	@Column
	private Integer type;
	/**
	 * 关联id
	 **/
	@Column
	private String joinId;

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
	 * 炮弹基础消耗
	 **/
	public Double getFireConsume() {
		return this.fireConsume;
	}

	/**
	 * 炮弹基础消耗
	 **/
	public void setFireConsume(Double fireConsume) {
		this.fireConsume = fireConsume;
	}

	/**
	 * 炮弹攻击力
	 **/
	public Double getFireAttack() {
		return this.fireAttack;
	}

	/**
	 * 炮弹攻击力
	 **/
	public void setFireAttack(Double fireAttack) {
		this.fireAttack = fireAttack;
	}

	/**
	 * 整体控制基数
	 **/
	public Double getControllerBodyBaseNum() {
		return this.controllerBodyBaseNum;
	}

	/**
	 * 整体控制基数
	 **/
	public void setControllerBodyBaseNum(Double controllerBodyBaseNum) {
		this.controllerBodyBaseNum = controllerBodyBaseNum;
	}

	/**
	 * 玩家基数
	 **/
	public Double getControllerPlayerBaseNum() {
		return this.controllerPlayerBaseNum;
	}

	/**
	 * 玩家基数
	 **/
	public void setControllerPlayerBaseNum(Double controllerPlayerBaseNum) {
		this.controllerPlayerBaseNum = controllerPlayerBaseNum;
	}

	/**
	 * 幅度基数
	 **/
	public Double getControllerFuDuBaseNum() {
		return this.controllerFuDuBaseNum;
	}

	/**
	 * 幅度基数
	 **/
	public void setControllerFuDuBaseNum(Double controllerFuDuBaseNum) {
		this.controllerFuDuBaseNum = controllerFuDuBaseNum;
	}

	/**
	 * 捕获基数
	 **/
	public Double getControllerCatchBaseNum() {
		return this.controllerCatchBaseNum;
	}

	/**
	 * 捕获基数
	 **/
	public void setControllerCatchBaseNum(Double controllerCatchBaseNum) {
		this.controllerCatchBaseNum = controllerCatchBaseNum;
	}

	/**
	 * 鱼基础生命值
	 **/
	public Double getFishBaseHpNum() {
		return this.fishBaseHpNum;
	}

	/**
	 * 鱼基础生命值
	 **/
	public void setFishBaseHpNum(Double fishBaseHpNum) {
		this.fishBaseHpNum = fishBaseHpNum;
	}

	/**
	 * 鱼生命系数
	 **/
	public Double getFishHpCoefficient() {
		return this.fishHpCoefficient;
	}

	/**
	 * 鱼生命系数
	 **/
	public void setFishHpCoefficient(Double fishHpCoefficient) {
		this.fishHpCoefficient = fishHpCoefficient;
	}

	/**
	 * 鱼生命基数
	 **/
	public Double getFishBaseHp() {
		return this.fishBaseHp;
	}

	/**
	 * 鱼生命基数
	 **/
	public void setFishBaseHp(Double fishBaseHp) {
		this.fishBaseHp = fishBaseHp;
	}

	/**
	 * 得分基数
	 **/
	public Double getGoldBaseNum() {
		return this.goldBaseNum;
	}

	/**
	 * 得分基数
	 **/
	public void setGoldBaseNum(Double goldBaseNum) {
		this.goldBaseNum = goldBaseNum;
	}

	/**
	 * 得分系数
	 **/
	public Double getGoldCoefficient() {
		return this.goldCoefficient;
	}

	/**
	 * 得分系数
	 **/
	public void setGoldCoefficient(Double goldCoefficient) {
		this.goldCoefficient = goldCoefficient;
	}

	/**
	 * 类型(0:公共,1:区域,2:个人)
	 **/
	public Integer getType() {
		return this.type;
	}

	/**
	 * 类型(0:公共,1:区域,2:个人)
	 **/
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 关联id
	 **/
	public String getJoinId() {
		return this.joinId;
	}

	/**
	 * 关联id
	 **/
	public void setJoinId(String joinId) {
		this.joinId = joinId;
	}
}
