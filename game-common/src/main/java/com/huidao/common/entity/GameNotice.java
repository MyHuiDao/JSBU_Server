package com.huidao.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.yehebl.orm.anno.Column;
import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 游戏公告
 */
@Entity("game_notice")
public class GameNotice implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 内容
	 **/
	@Column
	private String centen;
	/**
	 * 类型(0:首页公告)
	 **/
	@Column
	private String type;
	/**
	 * 状态(0:未发布,1:已发布)
	 **/
	@Column
	private String status;
	/**
	 * 创建时间
	 **/
	@Column
	private Date createTime;
	/**
	 * 排序
	 **/
	@Column
	private Integer seq;
	/**
	 * 发布时间
	 **/
	@Column
	private Date releaseTime;

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
	 * 内容
	 **/
	public String getCenten() {
		return this.centen;
	}

	/**
	 * 内容
	 **/
	public void setCenten(String centen) {
		this.centen = centen;
	}

	/**
	 * 类型(0:首页公告)
	 **/
	public String getType() {
		return this.type;
	}

	/**
	 * 类型(0:首页公告)
	 **/
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 状态(0:未发布,1:已发布)
	 **/
	public String getStatus() {
		return this.status;
	}

	/**
	 * 状态(0:未发布,1:已发布)
	 **/
	public void setStatus(String status) {
		this.status = status;
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
	 * 排序
	 **/
	public Integer getSeq() {
		return this.seq;
	}

	/**
	 * 排序
	 **/
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	/**
	 * 发布时间
	 **/
	public Date getReleaseTime() {
		return this.releaseTime;
	}

	/**
	 * 发布时间
	 **/
	public void setReleaseTime(Date releaseTime) {
		this.releaseTime = releaseTime;
	}
}