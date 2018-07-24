package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 在线客服消息
 */
@Entity("customer_service_msg")
public class CustomerServiceMsg implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 消息
	 **/
	@Column
	private String msg;
	/**
	 * 游戏用户id
	 **/
	@Column
	private String userId;
	/**
	 * 客服id
	 **/
	@Column
	private String sysUserId;
	/**
	 * 类型:(0:客服发出的,1:用户发出的)
	 **/
	@Column
	private Integer type;
	/**
	 * 状态(0:已读,1:未读)
	 **/
	@Column
	private Integer status;
	/**
	 * 发送时间
	 **/
	@Column
	private Date createTime;
	/**
	 * 读取时间
	 **/
	@Column
	private Date readTime;

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
	 * 消息
	 **/
	public String getMsg() {
		return this.msg;
	}

	/**
	 * 消息
	 **/
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 游戏用户id
	 **/
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 游戏用户id
	 **/
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 客服id
	 **/
	public String getSysUserId() {
		return this.sysUserId;
	}

	/**
	 * 客服id
	 **/
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	/**
	 * 类型:(0:客服发出的,1:用户发出的)
	 **/
	public Integer getType() {
		return this.type;
	}

	/**
	 * 类型:(0:客服发出的,1:用户发出的)
	 **/
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 状态(0:已读,1:未读)
	 **/
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 状态(0:已读,1:未读)
	 **/
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 发送时间
	 **/
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 发送时间
	 **/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 读取时间
	 **/
	public Date getReadTime() {
		return this.readTime;
	}

	/**
	 * 读取时间
	 **/
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
}
