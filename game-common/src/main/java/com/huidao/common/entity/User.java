package com.huidao.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.yehebl.orm.anno.Column;
import com.yehebl.orm.anno.Entity;
import com.yehebl.orm.anno.Id;
import com.yehebl.orm.enums.IdType;

/**
 * 用户表
 */
@Entity("user")
public class User implements Serializable {
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
	 * 用户id
	 **/
	@Column
	private Integer code;
	/**
	 * 账号
	 **/
	@Column
	private String account;
	/**
	 * 密码
	 **/
	@Column
	private String password;
	/**
	 * 盐
	 **/
	@Column
	private String salt;
	/**
	 * 微信id
	 **/
	@Column
	private String weixinId;
	/**
	 * 设备id
	 **/
	@Column
	private String deviceId;
	/**
	 * 头像
	 **/
	@Column
	private String headImg;
	/**
	 * 性别
	 **/
	@Column
	private String sex;
	/**
	 * 手机号
	 **/
	@Column
	private String mobile;
	/**
	 * 是否绑定手机0:未绑定，1:已绑定
	 **/
	@Column
	private String isBind;
	/**
	 * 玩家类型 免费玩家：free,付费玩家paid
	 **/
	@Column
	private String userType;
	/**
	 * 玩家状态 0：正常 1：禁用
	 **/
	@Column
	private String userState;
	/**
	 * 体验币
	 **/
	@Column
	private Long experience;
	/**
	 * 金币
	 **/
	@Column
	private Long gold;
	/**
	 * vip等级
	 **/
	@Column
	private Integer vipLevel;
	/**
	 * 是否为vip(0:非会员,1:会员)
	 **/
	@Column
	private String isVip;
	/**
	 * 推荐人游戏用户id
	 **/
	@Column
	private String recommendUserId;
	/**
	 * 微信openid
	 **/
	@Column
	private String weixinOpenId;
	/**
	 * 创建时间
	 **/
	@Column
	private Date createTime;
	/**
	 * 创建ip
	 **/
	@Column
	private String createIp;

	/**
	 * 锁定金币
	 */
	@Column
	private Long lockGold;

	/**
	 * 微信unionId
	 */
	@Column
	private String unionId;

	/**
	 * 总记录数
	 */
	private String count;

	/**
	 * 时间
	 */
	private String date;

	/**
	 * 几级代理
	 */
	private String agenLevel;

	/**
	 * 在线时长
	 */
	private String onlineTime;

	/**
	 * 游戏名称
	 */
	private String gameName;

	/**
	 * 上家ID
	 */
	private String topId;

	/**
	 * 代理时间
	 */
	private String auditDate;

	/**
	 * 充值金额
	 */
	private String amount;

	/**
	 * 后台管理账号状态
	 */
	private String status;
	
	/**
	 * 推荐人code
	 */
	private String parentCode;
	
	
	/**
	 * 代理级别
	 */
	private String agentName;

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
	 * 用户id
	 **/
	public Integer getCode() {
		return this.code;
	}

	/**
	 * 用户id
	 **/
	public void setCode(Integer code) {
		this.code = code;
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
	 * 微信id
	 **/
	public String getWeixinId() {
		return this.weixinId;
	}

	/**
	 * 微信id
	 **/
	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	/**
	 * 设备id
	 **/
	public String getDeviceId() {
		return this.deviceId;
	}

	/**
	 * 设备id
	 **/
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * 头像
	 **/
	public String getHeadImg() {
		return this.headImg;
	}

	/**
	 * 头像
	 **/
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	/**
	 * 性别
	 **/
	public String getSex() {
		return this.sex;
	}

	/**
	 * 性别
	 **/
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 手机号
	 **/
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 手机号
	 **/
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 是否绑定手机0:未绑定，1:已绑定
	 **/
	public String getIsBind() {
		return this.isBind;
	}

	/**
	 * 是否绑定手机0:未绑定，1:已绑定
	 **/
	public void setIsBind(String isBind) {
		this.isBind = isBind;
	}

	/**
	 * 玩家类型 免费玩家：free,付费玩家paid
	 **/
	public String getUserType() {
		return this.userType;
	}

	/**
	 * 玩家类型 免费玩家：free,付费玩家paid
	 **/
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * 玩家状态 0：正常 1：禁用
	 **/
	public String getUserState() {
		return this.userState;
	}

	/**
	 * 玩家状态 0：正常 1：禁用
	 **/
	public void setUserState(String userState) {
		this.userState = userState;
	}

	/**
	 * 体验币
	 **/
	public Long getExperience() {
		return this.experience;
	}

	/**
	 * 体验币
	 **/
	public void setExperience(Long experience) {
		this.experience = experience;
	}

	/**
	 * 金币
	 **/
	public Long getGold() {
		return this.gold;
	}

	/**
	 * 金币
	 **/
	public void setGold(Long gold) {
		this.gold = gold;
	}

	/**
	 * vip等级
	 **/
	public Integer getVipLevel() {
		return this.vipLevel;
	}

	/**
	 * vip等级
	 **/
	public void setVipLevel(Integer vipLevel) {
		this.vipLevel = vipLevel;
	}

	/**
	 * 是否为vip(0:非会员,1:会员)
	 **/
	public String getIsVip() {
		return this.isVip;
	}

	/**
	 * 是否为vip(0:非会员,1:会员)
	 **/
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	/**
	 * 推荐人游戏用户id
	 **/
	public String getRecommendUserId() {
		return recommendUserId;
	}

	/**
	 * 推荐人游戏用户id
	 **/
	public void setRecommendUserId(String recommendUserId) {
		this.recommendUserId = recommendUserId;
	}

	/**
	 * 微信openid
	 **/
	public String getWeixinOpenId() {
		return this.weixinOpenId;
	}

	/**
	 * 微信openid
	 **/
	public void setWeixinOpenId(String weixinOpenId) {
		this.weixinOpenId = weixinOpenId;
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
	 * 创建ip
	 **/
	public String getCreateIp() {
		return this.createIp;
	}

	/**
	 * 创建ip
	 **/
	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	/**
	 * 锁定金币
	 * 
	 * @return
	 */
	public Long getLockGold() {
		return lockGold;
	}

	/**
	 * 锁定金币
	 * 
	 * @return
	 */
	public void setLockGold(Long lockGold) {
		this.lockGold = lockGold;
	}

	/**
	 * 微信unionId
	 */
	public String getUnionId() {
		return unionId;
	}

	/**
	 * 微信unionId
	 */
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	/**
	 * 总记录数
	 */
	public String getCount() {
		return count;
	}

	/**
	 * 总记录数
	 */
	public void setCount(String count) {
		this.count = count;
	}

	/**
	 * 时间
	 * 
	 * @return
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 时间
	 * 
	 * @return
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * 代理等级
	 * 
	 * @return
	 */
	public String getAgenLevel() {
		return agenLevel;
	}

	/**
	 * 代理等级
	 * 
	 * @param agenLevel
	 */
	public void setAgenLevel(String agenLevel) {
		this.agenLevel = agenLevel;
	}

	/**
	 * 在线时长
	 * 
	 * @return
	 */
	public String getOnlineTime() {
		return onlineTime;
	}

	/**
	 * 在线时长
	 * 
	 * @return
	 */
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}

	/**
	 * 游戏名称
	 * 
	 * @return
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * 游戏名称
	 * 
	 * @return
	 */
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	/**
	 * 上家ID
	 * 
	 * @return
	 */
	public String getTopId() {
		return topId;
	}

	/**
	 * 上家ID
	 * 
	 * @return
	 */
	public void setTopId(String topId) {
		this.topId = topId;
	}

	/**
	 * 代理时间
	 * 
	 * @return
	 */
	public String getAuditDate() {
		return auditDate;
	}

	/**
	 * 代理时间
	 * 
	 * @return
	 */
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	/**
	 * 充值金额
	 * 
	 * @return
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * 充值金额
	 * 
	 * @return
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * 后台管理账号状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 后台管理账号状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 推荐人id
	 * @return
	 */
	public String getParentCode() {
		return parentCode;
	}

	/**
	 * 推荐人id
	 * @param parentCode
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	/**
	 * 代理级别名称
	 * @param agentName
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * 代理级别名称
	 * @param agentName
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	

}
