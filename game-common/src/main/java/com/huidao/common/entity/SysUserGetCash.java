package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 用户提现记录表
 */
@Entity("sys_user_get_cash")
public class SysUserGetCash implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 提现金额
	 **/
	@Column
	private BigDecimal money;
	/**
	 * 提现时间
	 **/
	@Column
	private Date createTime;
	/**
	 * 提现人sysuserid
	 **/
	@Column
	private String sysUserId;
	/**
	 * 游戏账号扣除金币
	 **/
	@Column
	private Long gold;
	/**
	 * 代理账号扣除余额
	 **/
	@Column
	private BigDecimal balance;
	/**
	 * 支付宝账号
	 **/
	@Column
	private String zhifubaoAccount;
	/**
	 * 支付宝名称
	 **/
	@Column
	private String zhifubaoName;
	
	
	/**
	 * 提现类型(0:赢钱提现，1：输钱提现，2：代理提现)
	 */
	@Column
	private Integer type;
	
	/**
	 * 状态：0，用于高危用户判断，1：不用于高危用户判断
	 */
	@Column
	private Integer status;
	/**
	 * 用户编码
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
	 * 提现金额
	 **/
	public BigDecimal getMoney() {
		return this.money;
	}

	/**
	 * 提现金额
	 **/
	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	/**
	 * 提现时间
	 **/
	public Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 提现时间
	 **/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 提现人sysuserid
	 **/
	public String getSysUserId() {
		return this.sysUserId;
	}

	/**
	 * 提现人sysuserid
	 **/
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	/**
	 * 游戏账号扣除金币
	 **/
	public Long getGold() {
		return this.gold;
	}

	/**
	 * 游戏账号扣除金币
	 **/
	public void setGold(Long gold) {
		this.gold = gold;
	}

	/**
	 * 代理账号扣除余额
	 **/
	public BigDecimal getBalance() {
		return this.balance;
	}

	/**
	 * 代理账号扣除余额
	 **/
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * 支付宝账号
	 **/
	public String getZhifubaoAccount() {
		return this.zhifubaoAccount;
	}

	/**
	 * 支付宝账号
	 **/
	public void setZhifubaoAccount(String zhifubaoAccount) {
		this.zhifubaoAccount = zhifubaoAccount;
	}

	/**
	 * 支付宝名称
	 **/
	public String getZhifubaoName() {
		return this.zhifubaoName;
	}

	/**
	 * 支付宝名称
	 **/
	public void setZhifubaoName(String zhifubaoName) {
		this.zhifubaoName = zhifubaoName;
	}

	/**
	 * 用户编码
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 用户编码
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
	 * 提现类型(0:赢钱提现，1：输钱提现，2：代理提现)
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * 提现类型(0:赢钱提现，1：输钱提现，2：代理提现)
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 状态：0，用于高危用户判断，1：不用于高危用户判断
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 状态：0，用于高危用户判断，1：不用于高危用户判断
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	
}
