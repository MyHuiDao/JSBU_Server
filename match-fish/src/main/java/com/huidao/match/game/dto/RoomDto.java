package com.huidao.match.game.dto;

import java.util.ArrayList;
import java.util.List;

import com.huidao.match.game.enums.RoomStatus;

/**
 * 鱼儿赛跑房间类
 * 
 * @author lenovo
 *
 */
public class RoomDto {

	// 房间id
	private String roomId;

	// 在线人数
	private Integer onlineUsers;

	// 房间状态
	private RoomStatus status;

	// 房间用户列表
	private List<String> userLists = new ArrayList<String>();

	// 创建时间
	private long createTime = System.currentTimeMillis();

	// 开奖时间
	private long lotteryTime;

	// 倒计时
	private long countdown;

	// 多长时间开奖
	private long seconds;

	private int maxUser;

	/**
	 * 开奖期数Id
	 */
	private String openPrezeId;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Integer getOnlineUsers() {
		return onlineUsers;
	}

	public void setOnlineUsers(Integer onlineUsers) {
		this.onlineUsers = onlineUsers;
	}

	public RoomStatus getStatus() {
		return status;
	}

	public void setStatus(RoomStatus status) {
		this.status = status;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public List<String> getUserLists() {
		return userLists;
	}

	public void setUserLists(List<String> userLists) {
		this.userLists = userLists;
	}

	public String getOpenPrezeId() {
		return openPrezeId;
	}

	public void setOpenPrezeId(String openPrezeId) {
		this.openPrezeId = openPrezeId;
	}

	public long getLotteryTime() {
		return lotteryTime;
	}

	public void setLotteryTime(long lotteryTime) {
		this.lotteryTime = lotteryTime;
	}

	public long getCountdown() {
		countdown = this.seconds - ((System.currentTimeMillis() - this.getLotteryTime()) / 1000);
		if (countdown < 0) {
			countdown = 0;
		}
		return countdown;
	}

	public void setCountdown(long countdown) {
		this.countdown = countdown;
	}

	public long getSeconds() {
		return seconds;
	}

	public void setSeconds(long seconds) {
		this.seconds = seconds;
	}

	public int getMaxUser() {
		return maxUser;
	}

	public void setMaxUser(int maxUser) {
		this.maxUser = maxUser;
	}

}
