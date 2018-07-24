package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 用户登录日志
 */
@Entity("user_login")
public class UserLogin implements Serializable {
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
	 * ip
	 **/
	@Column
	private String ip;
	/**
	 * 登录时间
	 **/
	@Column
	private Date loginTime;
	/**
	 * 统计记录
	 */
	private Integer count;
	/**
	 * 时间
	 */
	private String time;
	/**
	 * 用户code
	 */
	private String code;
	/**
	 * 用户昵称
	 */
	private String nickName;

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
	 * ip
	 **/
	public String getIp() {
		return this.ip;
	}

	/**
	 * ip
	 **/
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 登录时间
	 **/
	public Date getLoginTime() {
		return this.loginTime;
	}

	/**
	 * 登录时间
	 **/
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * 统计记录
	 * 
	 * @return
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * 统计记录
	 * 
	 * @return
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * 统计时间
	 * 
	 * @return
	 */
	public String getTime() {
		return time;
	}

	/**
	 * 统计时间
	 * 
	 * @return
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * 用户code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 用户code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 用户昵称
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * 用户昵称
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
