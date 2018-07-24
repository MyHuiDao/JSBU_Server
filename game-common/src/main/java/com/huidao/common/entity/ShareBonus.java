package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 分享奖励
 */
@Entity("share_bouns")
public class ShareBonus implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 次数
	 **/
	@Column
	private Integer count;
	/**
	 * 金币
	 **/
	@Column
	private Integer gold;

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
	 * 次数
	 **/
	public Integer getCount() {
		return this.count;
	}

	/**
	 * 次数
	 **/
	public void setCount(Integer count) {
		this.count = count;
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
}
