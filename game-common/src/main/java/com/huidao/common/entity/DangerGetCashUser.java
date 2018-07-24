package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 高危提现用户
 */
@Entity("danger_get_cash_user")
public class DangerGetCashUser implements Serializable {
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
	 * 状态(0:高危用户,1:解除的高危用户)
	 **/
	@Column
	private Integer status;
	/**
	 * 系统操作人
	 **/
	@Column
	private String sysUserId;
	/**
	 * 更新时间
	 **/
	@Column
	private Date updateDate;
	/**
	 * 创建时间
	 **/
	@Column
	private Date createDate;

	/**
	 * 用户code
	 */
	private String code;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 操作人名称
	 */
	private String sysNickName;

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
	 * 状态(0:高危用户,1:解除的高危用户)
	 **/
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 状态(0:高危用户,1:解除的高危用户)
	 **/
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 系统操作人
	 **/
	public String getSysUserId() {
		return this.sysUserId;
	}

	/**
	 * 系统操作人
	 **/
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	/**
	 * 更新时间
	 **/
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 更新时间
	 **/
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 创建时间
	 **/
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 创建时间
	 **/
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 用户code
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 用户code
	 * 
	 * @return
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 用户昵称
	 * 
	 * @return
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * 用户昵称
	 * 
	 * @return
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * 操作人昵称
	 * 
	 * @return
	 */
	public String getSysNickName() {
		return sysNickName;
	}

	/**
	 * 操作人昵称
	 * 
	 * @return
	 */
	public void setSysNickName(String sysNickName) {
		this.sysNickName = sysNickName;
	}

}
