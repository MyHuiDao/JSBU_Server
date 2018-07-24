package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

@Entity("fish_type_setting")
public class FishTypeSetting implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 级别
	 **/
	@Column
	private Integer level;
	/**
	 * 初始数量
	 **/
	@Column
	private Integer initCount;
	/**
	 * 最大数量
	 **/
	@Column
	private Integer maxCount;
	/**
	 * 描述
	 **/
	@Column
	private String name;
	
	/**
	 * 速度
	 */
	private String speed="0.5,0.6,0.7,0.8,0.9,1,1.2,1.3";

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
	 * 级别
	 **/
	public Integer getLevel() {
		return this.level;
	}

	/**
	 * 级别
	 **/
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 初始数量
	 **/
	public Integer getInitCount() {
		return this.initCount;
	}

	/**
	 * 初始数量
	 **/
	public void setInitCount(Integer initCount) {
		this.initCount = initCount;
	}

	/**
	 * 最大数量
	 **/
	public Integer getMaxCount() {
		return this.maxCount;
	}

	/**
	 * 最大数量
	 **/
	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	/**
	 * 描述
	 **/
	public String getName() {
		return this.name;
	}

	/**
	 * 描述
	 **/
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * 获取生命值
	 * @return
	 */
	public int getHp() {
		return (int) Math.floor(20 * Math.pow((4.6 + 1) / 4.6, getLevel() - 1));
	}

	/**
	 * 获取得分
	 * @return
	 */
	public int getGold() {
		int dfxs = 0;
		if (getLevel() > 10) {
			dfxs = 10;
		}
		return (int) (Math.round((getHp() - getAdjustGold() - dfxs) / 10) * 10);
	}

	
	/**
	 * 获取得分调整值
	 * @return
	 */
	public double getAdjustGold() {
		return Math.round((double) getHp() / 5D);
	}

	/**
	 * 获取刷新率
	 * @return
	 */
	public double getRefresh() {
		return (double) Math.round((22.7777 - getLevel()) / 205 * 5 * 10000) / 100D;
	}

	/**
	 * 速度
	 */
	public String getSpeed() {
		return speed;
	}

	/**
	 * 速度
	 */
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	
	
}
