package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 后台管理员用户
 */
@Entity("sys_user")
public class SysUser implements Serializable {

	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 昵称
	 **/
	@Column
	private String nickName;
	/**
	 * 密码
	 **/
	@Column
	private String password;
	/**
	 * 账号
	 **/
	@Column
	private String account;
	/**
	 * 盐
	 **/
	@Column
	private String salt;
	/**
	 * 用户类型(admin:管理员)
	 **/
	@Column
	private String type;
	/**
	 * 用户状态:0正常,1:禁用
	 **/
	@Column
	private Integer status;
	/**
	 * 游戏用户id
	 **/
	@Column
	private String gameUserId;
	/**
	 * 创建时间
	 */
	@Column
	private Date createDate;

	/**
	 * 更新时间
	 */
	@Column
	private Date updateDate;

	/**
	 * 最后操作人
	 */
	@Column
	private String sysUserId;
	/**
	 * 代理级别id
	 **/
	@Column
	private String agenLevelId;

	/**
	 * 钱
	 */
	@Column
	private BigDecimal money;

	/**
	 * 用户code
	 */
	private String code;

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
	 * 昵称
	 **/
	public String getNickName() {
		return this.nickName;
	}

	/**
	 * 昵称
	 **/
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * 密码
	 **/
	public String getPassword() {
		return this.password;
	}

	/**
	 * 密码
	 **/
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 账号
	 **/
	public String getAccount() {
		return this.account;
	}

	/**
	 * 账号
	 **/
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 盐
	 **/
	public String getSalt() {
		return this.salt;
	}

	/**
	 * 盐
	 **/
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * 用户类型(admin:管理员)
	 **/
	public String getType() {
		return this.type;
	}

	/**
	 * 用户类型(admin:管理员)
	 **/
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 用户状态:0正常,1:禁用
	 **/
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 用户状态:0正常,1:禁用
	 **/
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 游戏用户id
	 **/
	public String getGameUserId() {
		return this.gameUserId;
	}

	/**
	 * 游戏用户id
	 **/
	public void setGameUserId(String gameUserId) {
		this.gameUserId = gameUserId;
	}

	/**
	 * 创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * 创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 更新时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * 更新时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 最后操作人
	 * 
	 * @return
	 */
	public String getSysUserId() {
		return sysUserId;
	}

	/**
	 * 最后操作人
	 * 
	 * @param sysUserId
	 */
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	/**
	 * 代理级别id
	 **/
	public String getAgenLevelId() {
		return this.agenLevelId;
	}

	/**
	 * 代理级别id
	 **/
	public void setAgenLevelId(String agenLevelId) {
		this.agenLevelId = agenLevelId;
	}

	/**
	 * 钱
	 * 
	 * @return
	 */
	public BigDecimal getMoney() {
		return money;
	}

	/**
	 * 钱
	 * 
	 * @param money
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
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

}
