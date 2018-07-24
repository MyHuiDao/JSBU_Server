package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 充值记录表
 */
@Entity("pay_log")
public class PayLog implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 金币商品id
	 **/
	@Column
	private String goldId;
	/**
	 * 金币
	 **/
	@Column
	private Integer gold;
	/**
	 * 赠送金币
	 **/
	@Column
	private Integer freeGold;
	/**
	 * 状态(0:未支付,1:已支付)
	 **/
	@Column
	private Integer status;
	/**
	 * 游戏用户id
	 **/
	@Column
	private String userId;
	/**
	 * 价格
	 **/
	@Column
	private BigDecimal price;
	/**
	 * 订单时间
	 **/
	@Column
	private Date createDate;
	/**
	 * 支付回调时间
	 **/
	@Column
	private Date payDate;
	/**
	 * 用户code
	 */
	private String code;
	/**
	 * 用户昵称
	 */
	private String nickName;
	
	/**
	 * 苹果订单id
	 */
	@Column
	private String appleOrderId;
	
	/**
	 * 支付类型，0：充值金币，1：购买代理
	 */
	@Column
	private Integer type;
	
	
	/***
	 * 后台操作人的id
	 */
	@Column
	private String createSysUserId;


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
	 * 金币商品id
	 **/
	public String getGoldId() {
		return this.goldId;
	}

	/**
	 * 金币商品id
	 **/
	public void setGoldId(String goldId) {
		this.goldId = goldId;
	}

	/**
	 * 金币
	 **/
	public Integer getGold() {
		return this.gold;
	}

	/**
	 * 金币
	 **/
	public void setGold(Integer gold) {
		this.gold = gold;
	}

	/**
	 * 赠送金币
	 **/
	public Integer getFreeGold() {
		return this.freeGold;
	}

	/**
	 * 赠送金币
	 **/
	public void setFreeGold(Integer freeGold) {
		this.freeGold = freeGold;
	}

	/**
	 * 状态(0:未支付,1:已支付)
	 **/
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 状态(0:未支付,1:已支付)
	 **/
	public void setStatus(Integer status) {
		this.status = status;
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
	 * 价格
	 **/
	public BigDecimal getPrice() {
		return this.price;
	}

	/**
	 * 价格
	 **/
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * 订单时间
	 **/
	public Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 订单时间
	 **/
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 支付回调时间
	 **/
	public Date getPayDate() {
		return this.payDate;
	}

	/**
	 * 支付回调时间
	 **/
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
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
	 * 苹果订单id
	 */
	public String getAppleOrderId() {
		return appleOrderId;
	}

	/**
	 * 苹果订单id
	 */
	public void setAppleOrderId(String appleOrderId) {
		this.appleOrderId = appleOrderId;
	}

	/**
	 * 支付类型，0：充值金币，1：购买代理
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * 支付类型，0：充值金币，1：购买代理
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/***
	 * 后台操作人的id
	 */
	public String getCreateSysUserId() {
		return createSysUserId;
	}

	/***
	 * 后台操作人的id
	 */
	public void setCreateSysUserId(String createSysUserId) {
		this.createSysUserId = createSysUserId;
	}

	
	
	
	
}
