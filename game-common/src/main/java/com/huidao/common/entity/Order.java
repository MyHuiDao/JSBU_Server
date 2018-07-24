package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 订单表
 */
@Entity("order")
public class Order implements Serializable {
	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 商品id
	 **/
	@Column
	private String goodsId;
	/**
	 * 游戏用户id
	 **/
	@Column
	private String gameUserId;
	/**
	 * 手机号码
	 **/
	@Column
	private String mobile;
	/**
	 * 收件人
	 **/
	@Column
	private String name;
	/**
	 * 快递单号
	 **/
	@Column
	private String number;
	/**
	 * 快递企业名称
	 **/
	@Column
	private String companyName;
	/**
	 * 收件地址
	 **/
	@Column
	private String address;
	/**
	 * 备注
	 **/
	@Column
	private String desc;
	/**
	 * 状态(0:未处理,1::已处理)
	 **/
	@Column
	private Integer status;
	/**
	 * 订单时间
	 **/
	@Column
	private Date createDate;
	/**
	 * 处理时间
	 **/
	@Column
	private Date execDate;
	
	/**
	 * 商品名称
	 */
	private String goodsName;
	
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 用户Code
	 */
	private Integer code;

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
	 * 商品id
	 **/
	public String getGoodsId() {
		return this.goodsId;
	}

	/**
	 * 商品id
	 **/
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
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
	 * 手机号码
	 **/
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 手机号码
	 **/
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 收件人
	 **/
	public String getName() {
		return this.name;
	}

	/**
	 * 收件人
	 **/
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 快递单号
	 **/
	public String getNumber() {
		return this.number;
	}

	/**
	 * 快递单号
	 **/
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * 快递企业名称
	 **/
	public String getCompanyName() {
		return this.companyName;
	}

	/**
	 * 快递企业名称
	 **/
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * 收件地址
	 **/
	public String getAddress() {
		return this.address;
	}

	/**
	 * 收件地址
	 **/
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 备注
	 **/
	public String getDesc() {
		return this.desc;
	}

	/**
	 * 备注
	 **/
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * 状态(0:未处理,1::已处理)
	 **/
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * 状态(0:未处理,1::已处理)
	 **/
	public void setStatus(Integer status) {
		this.status = status;
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
	 * 处理时间
	 **/
	public Date getExecDate() {
		return this.execDate;
	}

	/**
	 * 处理时间
	 **/
	public void setExecDate(Date execDate) {
		this.execDate = execDate;
	}


	/**
	 * 商品名称
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 商品名称
	 * @param goodsName
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 用户昵称
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * 用户昵称
	 * @param nickName
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * 用户code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * 用户code
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	
	
}
