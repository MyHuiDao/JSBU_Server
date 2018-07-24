package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 邮件
 */
@Entity("game_email")
public class GameEmail implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 标题
	 **/
	@Column
	private String title;
	/**
	 * 内容
	 **/
	@Column
	private String content;
	/**
	 * 创建时间
	 **/
	@Column
	private Date createTime;
	/**
	 * 失效时间
	 **/
	@Column
	private Date invalidTime;
	/**
	 * 状态(0:草稿,1:发送,3:删除)
	 **/
	@Column
	private Integer status;

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
	 * 标题
	 **/
	public String getTitle() {
		return this.title;
	}

	/**
	 * 标题
	 **/
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 内容
	 **/
	public String getContent() {
		return this.content;
	}

	/**
	 * 内容
	 **/
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 创建时间
	 **/
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 创建时间
	 **/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 失效时间
	 **/
	public Date getInvalidTime() {
		return this.invalidTime;
	}

	/**
	 * 失效时间
	 **/
	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	/**
	 * 状态(0:草稿,1:发送,3:删除)
	 **/
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 状态(0:草稿,1:发送,3:删除)
	 **/
	public void setStatus(Integer status) {
		this.status = status;
	}
}
