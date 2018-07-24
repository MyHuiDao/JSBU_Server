package com.huidao.fishing.game.dto;

/**
 * 可控数值
 * 
 * @author Administrator
 *
 */
public class FishControllerPropertyDto {
	/**
	 * 炮弹等级
	 */
	private int fireLevel = 1;

	/**
	 * 发射数量
	 */
	private int fireSendNum = 1;

	/**
	 * 鱼等级
	 */
	private int fishLevel = 1;

	/**
	 * 房间倍数
	 */
	private int roomMultiple = 1000;

	/**
	 * 整体档位
	 */
	private double wholeDangWei = 5;

	/**
	 * 玩家档位
	 */
	private double playerDangWei = 5;

	/**
	 * 鱼阵档位
	 */
	private double fishDangWei = 3;

	/**
	 * 幅度档位
	 */
	private double fuDuDangWei = 1;

	public int getFireLevel() {
		return fireLevel;
	}

	public void setFireLevel(int fireLevel) {
		this.fireLevel = fireLevel;
	}

	public int getFireSendNum() {
		return fireSendNum;
	}

	public void setFireSendNum(int fireSendNum) {
		this.fireSendNum = fireSendNum;
	}

	public int getFishLevel() {
		return fishLevel;
	}

	public void setFishLevel(int fishLevel) {
		this.fishLevel = fishLevel;
	}

	public int getRoomMultiple() {
		return roomMultiple;
	}

	public void setRoomMultiple(int roomMultiple) {
		this.roomMultiple = roomMultiple;
	}

	public double getWholeDangWei() {
		return wholeDangWei;
	}

	public void setWholeDangWei(double wholeDangWei) {
		this.wholeDangWei = wholeDangWei;
	}

	public double getPlayerDangWei() {
		return playerDangWei;
	}

	public void setPlayerDangWei(double playerDangWei) {
		this.playerDangWei = playerDangWei;
	}

	public double getFishDangWei() {
		return fishDangWei;
	}

	public void setFishDangWei(double fishDangWei) {
		this.fishDangWei = fishDangWei;
	}

	public double getFuDuDangWei() {
		return fuDuDangWei;
	}

	public void setFuDuDangWei(double fuDuDangWei) {
		this.fuDuDangWei = fuDuDangWei;
	}

}
