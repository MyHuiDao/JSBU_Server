package com.huidao.fishing.game.dto;

import java.util.UUID;

/**
 * 炮
 * 
 * @author Administrator
 *
 */
public class FireDto {
	public FireDto() {
		this.id=UUID.randomUUID().toString();
	}
	/**
	 * 炮级别
	 */
	private int level;
	
	/**
	 * 炮唯一标识
	 */
	private String id;

	/**
	 * 开炮者
	 */
	private String userId;

	/**
	 * 角度
	 */
	private Double angle;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getAngle() {
		return angle;
	}

	public void setAngle(Double angle) {
		this.angle = angle;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
