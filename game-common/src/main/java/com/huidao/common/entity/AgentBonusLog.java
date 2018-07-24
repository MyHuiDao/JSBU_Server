package com.huidao.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.yehebl.orm.anno.Column;
import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 代理分红记录表
 */
@Entity("agent_bonus_log")
public class AgentBonusLog implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 代理游戏用户id
	 **/
	@Column
	private String userId;
	/**
	 * 当前分红比例(%)
	 **/
	@Column
	private Integer bonusProportion;
	/**
	 * 充值记录表id
	 **/
	@Column
	private String payId;
	/**
	 * 实际获取分红
	 **/
	@Column
	private BigDecimal bonusMoney;
	/**
	 * 上级代理分红id
	 **/
	@Column
	private String parentBonusId;
	/**
	 * 分红记录时间
	 **/
	@Column
	private Date createDate;

	/**
	 * 游戏用户昵称
	 */
	private String userName;
	/**
	 * 代理用户昵称
	 */
	private String proxyName;
	/**
	 * 价格
	 */
	private BigDecimal price;

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
	 * 代理游戏用户id
	 **/
	public String getUserId() {
		return this.userId;
	}

	/**
	 * 代理游戏用户id
	 **/
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 当前分红比例(%)
	 **/
	public Integer getBonusProportion() {
		return this.bonusProportion;
	}

	/**
	 * 当前分红比例(%)
	 **/
	public void setBonusProportion(Integer bonusProportion) {
		this.bonusProportion = bonusProportion;
	}

	/**
	 * 充值记录表id
	 **/
	public String getPayId() {
		return this.payId;
	}

	/**
	 * 充值记录表id
	 **/
	public void setPayId(String payId) {
		this.payId = payId;
	}

	/**
	 * 实际获取分红
	 **/
	public BigDecimal getBonusMoney() {
		return this.bonusMoney;
	}

	/**
	 * 实际获取分红
	 **/
	public void setBonusMoney(BigDecimal bonusMoney) {
		this.bonusMoney = bonusMoney;
	}

	/**
	 * 上级代理分红id
	 **/
	public String getParentBonusId() {
		return this.parentBonusId;
	}

	/**
	 * 上级代理分红id
	 **/
	public void setParentBonusId(String parentBonusId) {
		this.parentBonusId = parentBonusId;
	}

	/**
	 * 游戏用户昵称
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 游戏用户昵称
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 代理用户昵称
	 */
	public String getProxyName() {
		return proxyName;
	}

	/**
	 * 代理用户昵称
	 */
	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}

	/**
	 * 价格
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * 价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
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
	 * 分红记录时间
	 **/
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 分红记录时间
	 **/
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
