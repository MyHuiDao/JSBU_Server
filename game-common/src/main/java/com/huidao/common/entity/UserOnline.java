package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 用户在线时长
 */
@Entity("user_online")
public class UserOnline implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 用户id
	 **/
	@Column
	private String userId;
	/**
	 * 游戏名称
	 **/
	@Column
	private String name;
	/**
	 * 在线开始时间
	 **/
	@Column
	private Date onlineTimeStart;
	/**
	 * 在线结束时间
	 **/
	@Column
	private Date onlineTimeEnd;
	/**
	 * 记录数
	 */
	private String count;
	/**
	 * 时间
	 */
	private String time;

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
	 * 用户id
	 **/
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 用户id
	 **/
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 游戏名称
	 **/
	public String getName() {
		return this.name;
	}

	/**
	 * 游戏名称
	 **/
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 在线开始时间
	 **/
	public Date getOnlineTimeStart() {
		return this.onlineTimeStart;
	}

	/**
	 * 在线开始时间
	 **/
	public void setOnlineTimeStart(Date onlineTimeStart) {
		this.onlineTimeStart = onlineTimeStart;
	}

	/**
	 * 在线结束时间
	 **/
	public Date getOnlineTimeEnd() {
		return this.onlineTimeEnd;
	}

	/**
	 * 在线结束时间
	 **/
	public void setOnlineTimeEnd(Date onlineTimeEnd) {
		this.onlineTimeEnd = onlineTimeEnd;
	}

	/**
	 * 记录数
	 */
	public String getCount() {
		return count;
	}

	/**
	 * 记录数
	 */
	public void setCount(String count) {
		this.count = count;
	}

	/**
	 * 时间
	 */
	public String getTime() {
		return time;
	}

	/**
	 * 时间
	 */
	public void setTime(String time) {
		this.time = time;
	}

}
