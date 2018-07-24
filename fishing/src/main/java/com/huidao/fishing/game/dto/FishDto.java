package com.huidao.fishing.game.dto;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 鱼类
 * @author lenovo
 *
 */
public class FishDto {
	
	
	/**
	 * 鱼唯一索引
	 */
	private String id;
	
	
	
	/**
	 * 获取金币开始区间
	 */
	private int getGoldStrart;
	
	/**
	 * 获取金币结束区间
	 */
	private int getGoldEnd;
	
	
	/**
	 * 鱼运动轨迹
	 */
	private String runPoint;
	
	
	
	/**
	 * 速度
	 */
	private double speed;
	
	
	/**
	 * 鱼阵标识
	 */
	private String group;
	
	/**
	 * 鱼级别
	 */
	private int level;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public int getGetGoldStrart() {
		return getGoldStrart;
	}

	public void setGetGoldStrart(int getGoldStrart) {
		this.getGoldStrart = getGoldStrart;
	}

	public int getGetGoldEnd() {
		return getGoldEnd;
	}

	public void setGetGoldEnd(int getGoldEnd) {
		this.getGoldEnd = getGoldEnd;
	}
	
	/**
	 * 获取最终得到金币
	 * @return
	 */
	public int getGold() {
		if(getGetGoldStrart()==getGetGoldEnd()) {
			return getGetGoldStrart();
		}
		return ThreadLocalRandom.current().nextInt(getGetGoldEnd()-getGetGoldStrart())+getGetGoldStrart();
	}

	public String getRunPoint() {
		return runPoint;
	}

	public void setRunPoint(String runPoint) {
		this.runPoint = runPoint;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
	
	
}
