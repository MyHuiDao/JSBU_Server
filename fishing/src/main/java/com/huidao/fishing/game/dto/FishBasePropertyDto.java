package com.huidao.fishing.game.dto;

/**
 * 捕鱼公式基本属性
 * 
 * @author Administrator
 *
 */
public class FishBasePropertyDto {
	/**
	 * 炮弹基础消耗
	 */
	private long fireConsume = 10;

	/**
	 * 炮弹攻击力
	 */
	private double fireAttack = 10;

	/**
	 * 控制整体基础
	 */
	private double controllerBodyBaseNum = 0.1;

	/**
	 * 玩家基数
	 */
	private double controllerPlayerBaseNum = 1.8;

	/**
	 * 幅度基数
	 */
	private double controllerFuDuBaseNum = 0.55;

	/**
	 * 捕获基数
	 */
	private double controllerCatchBaseNum = 1;

	/**
	 * 鱼基础生命值
	 */
	private double fishBaseHpNum = 20;

	/**
	 * 生命系数
	 */
	private double fishHpCoefficient = 4.6;

	/**
	 * 生命基数
	 */
	private double fishBaseHp = 1;

	/**
	 * 得分基数
	 */
	private double goldBaseNum = 10;

	/**
	 * 得分系数
	 */
	private double goldCoefficient = 5;

	public long getFireConsume() {
		return fireConsume;
	}

	public void setFireConsume(long fireConsume) {
		this.fireConsume = fireConsume;
	}

	public double getFireAttack() {
		return fireAttack;
	}

	public void setFireAttack(double fireAttack) {
		this.fireAttack = fireAttack;
	}

	public double getControllerBodyBaseNum() {
		return controllerBodyBaseNum;
	}

	public void setControllerBodyBaseNum(double controllerBodyBaseNum) {
		this.controllerBodyBaseNum = controllerBodyBaseNum;
	}

	public double getControllerPlayerBaseNum() {
		return controllerPlayerBaseNum;
	}

	public void setControllerPlayerBaseNum(double controllerPlayerBaseNum) {
		this.controllerPlayerBaseNum = controllerPlayerBaseNum;
	}

	public double getControllerFuDuBaseNum() {
		return controllerFuDuBaseNum;
	}

	public void setControllerFuDuBaseNum(double controllerFuDuBaseNum) {
		this.controllerFuDuBaseNum = controllerFuDuBaseNum;
	}

	public double getControllerCatchBaseNum() {
		return controllerCatchBaseNum;
	}

	public void setControllerCatchBaseNum(double controllerCatchBaseNum) {
		this.controllerCatchBaseNum = controllerCatchBaseNum;
	}

	public double getFishBaseHpNum() {
		return fishBaseHpNum;
	}

	public void setFishBaseHpNum(double fishBaseHpNum) {
		this.fishBaseHpNum = fishBaseHpNum;
	}

	public double getFishHpCoefficient() {
		return fishHpCoefficient;
	}

	public void setFishHpCoefficient(double fishHpCoefficient) {
		this.fishHpCoefficient = fishHpCoefficient;
	}

	public double getFishBaseHp() {
		return fishBaseHp;
	}

	public void setFishBaseHp(double fishBaseHp) {
		this.fishBaseHp = fishBaseHp;
	}

	public double getGoldBaseNum() {
		return goldBaseNum;
	}

	public void setGoldBaseNum(double goldBaseNum) {
		this.goldBaseNum = goldBaseNum;
	}

	public double getGoldCoefficient() {
		return goldCoefficient;
	}

	public void setGoldCoefficient(double goldCoefficient) {
		this.goldCoefficient = goldCoefficient;
	}

}
