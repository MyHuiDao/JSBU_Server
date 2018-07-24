package com.huidao.common.entity;

import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 代理级别
 */
@Entity("agen_level")
public class AgenLevel implements Serializable {
	public AgenLevel() {
	}

	public AgenLevel(BigDecimal price, Integer proportion1, Integer proportion2, Integer proportion3, Integer level,
			Date createDate, Date updateDate, String sysUserId, String name) {
		super();
		this.price = price;
		this.proportion1 = proportion1;
		this.proportion2 = proportion2;
		this.proportion3 = proportion3;
		this.level = level;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.sysUserId = sysUserId;
		this.name = name;
	}

	private static final long serialVersionUID = -1;
	/**
	 * 主键
	 **/
	@Id(IdType.uuid)
	@Column
	private String id;
	/**
	 * 价格
	 **/
	@Column
	private BigDecimal price;
	/**
	 * 二级分红(%)
	 **/
	@Column
	private Integer proportion2;
	/**
	 * 三级分红(%)
	 **/
	@Column
	private Integer proportion3;
	/**
	 * 级别
	 **/
	@Column
	private Integer level;
	/**
	 * 一级分红(%)
	 **/
	@Column
	private Integer proportion1;
	/**
	 * 创建时间
	 **/
	@Column
	private Date createDate;
	/**
	 * 更新时间
	 **/
	@Column
	private Date updateDate;
	/**
	 * 最后操作人
	 **/
	@Column
	private String sysUserId;
	/**
	 * 代理名称
	 **/
	@Column
	private String name;
	
	
	/**
	 * 对应商品金币id
	 * @param goldId
	 */
	@Column
	private String goldId;
	/**
	 * 用户code
	 */
	private String code;

	/**
	 * 用户昵称
	 */
	private String nickName;

	/**
	 * 用户手机号码
	 */
	private String mobile;

	/**
	 * 代理时间
	 */
	private String agencyTime;

	/**
	 * 累计收益
	 */
	private String income;

	/**
	 * 金币
	 */
	private String gold;

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
	 * 二级分红(%)
	 **/
	public Integer getProportion2() {
		return this.proportion2;
	}

	/**
	 * 二级分红(%)
	 **/
	public void setProportion2(Integer proportion2) {
		this.proportion2 = proportion2;
	}

	/**
	 * 三级分红(%)
	 **/
	public Integer getProportion3() {
		return this.proportion3;
	}

	/**
	 * 三级分红(%)
	 **/
	public void setProportion3(Integer proportion3) {
		this.proportion3 = proportion3;
	}

	/**
	 * 级别
	 **/
	public Integer getLevel() {
		return this.level;
	}

	/**
	 * 级别
	 **/
	public void setLevel(Integer level) {
		this.level = level;
	}

	/**
	 * 一级分红(%)
	 **/
	public Integer getProportion1() {
		return this.proportion1;
	}

	/**
	 * 一级分红(%)
	 **/
	public void setProportion1(Integer proportion1) {
		this.proportion1 = proportion1;
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
	 * 最后操作人
	 **/
	public String getSysUserId() {
		return this.sysUserId;
	}

	/**
	 * 最后操作人
	 **/
	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	/**
	 * 代理名称
	 **/
	public String getName() {
		return this.name;
	}

	/**
	 * 代理名称
	 **/
	public void setName(String name) {
		this.name = name;
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
	 * 昵称
	 * 
	 * @return
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * 昵称
	 * 
	 * @return
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * 手机
	 * 
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 手机
	 * 
	 * @return
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 累计收益
	 * 
	 * @return
	 */
	public String getIncome() {
		return income;
	}

	/**
	 * 累计收益
	 * 
	 * @return
	 */
	public void setIncome(String income) {
		this.income = income;
	}

	/**
	 * 代理时间
	 * 
	 * @return
	 */
	public String getAgencyTime() {
		return agencyTime;
	}

	/**
	 * 代理时间
	 * 
	 * @return
	 */
	public void setAgencyTime(String agencyTime) {
		this.agencyTime = agencyTime;
	}

	/**
	 * 金币
	 * 
	 * @return
	 */
	public String getGold() {
		return gold;
	}

	/**
	 * 金币
	 * 
	 * @return
	 */
	public void setGold(String gold) {
		this.gold = gold;
	}

	/**
	 * 对应商品金币id
	 * @param goldId
	 */
	public String getGoldId() {
		return goldId;
	}

	/**
	 * 对应商品金币id
	 * @param goldId
	 */
	public void setGoldId(String goldId) {
		this.goldId = goldId;
	}

	
	
}
