package com.huidao.common.dto;

import java.io.Serializable;

import com.yehebl.orm.anno.Dto;

@Dto
public class RankDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 游戏用户昵称
	 */
	private String nickName;
	
	/**
	 * 头像
	 */
	private String head;
	
	/**
	 * 数值
	 */
	private Double num;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}
	
	
	

}
