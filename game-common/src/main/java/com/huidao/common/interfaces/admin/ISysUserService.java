package com.huidao.common.interfaces.admin;

import java.math.BigDecimal;
import java.util.List;

import com.huidao.common.dto.MyPromotion;
import com.huidao.common.entity.AgenLevel;
import com.huidao.common.entity.SysPermission;
import com.huidao.common.entity.SysUser;
import com.huidao.common.entity.User;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

/**
 * 后台用户管理
 * 
 * @author Administrator
 *
 */
public interface ISysUserService {
	/**
	 * 查询后台用户列表
	 * 
	 * @param page
	 * @param size
	 * @param map
	 * @return
	 */
	public MsgDto<Page<SysUser>> findPage(Integer page, Integer size, String code, String nickName);

	/**
	 * 删除后台用户（假删）
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<String> delete(String id);

	/**
	 * 保存系统用户
	 * 
	 * @param user
	 * @return
	 */
	public MsgDto<String> add(SysUser user, String currentSysUserId);

	/**
	 * 获取单个系统用户
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<SysUser> get(String id);

	/**
	 * 设置系统用户，角色
	 * 
	 * @param userId
	 * @param roles
	 * @return
	 */
	public MsgDto<String> setSysUserRoles(String userId, List<String> roles);

	/**
	 * 获取用户拥有权限
	 * 
	 * @param userId
	 * @return
	 */
	public List<SysPermission> getPermissionByUserId(String userId);

	/**
	 * 登录
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	public MsgDto<SysUser> login(String account, String password, String ip);

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	public MsgDto<String> update(SysUser user, String currentSysUserId);

	/**
	 * 通过用户游戏id查询用户
	 * 
	 * @param userId
	 * @return
	 */
	public SysUser getGameUserId(String userId);

	/**
	 * 查询有使用代理级别的管理员
	 * 
	 * @param agenLevelId
	 * @return
	 */
	public List<SysUser> getAgenLevels(String agenLevelId);

	/**
	 * 提现到支付宝
	 * 
	 * @param sysUserId
	 * @param cash
	 * @param zhifubaoAccount
	 *            支付宝账号
	 * @param zhifubaoName
	 *            支付宝姓名
	 * @return
	 */
	public MsgDto<String> getCash(String sysUserId, Double cash, String zhifubaoAccount, String zhifubaoName,
			String phone, String yzCode);

	/**
	 * 显示个人中心资料
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<AgenLevel> personalCenter(String id);

	/**
	 * 显示我的推广奖金
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<Page<MyPromotion>> findMyPromotion(Integer page, Integer size, String id);

	/**
	 * 统计用户推广的总金额
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<BigDecimal> findCountMoney(String id);

	/**
	 * 查看我的下级代理推广奖金情况
	 * 
	 * @return
	 */
	public MsgDto<Page<MyPromotion>> findMySubagent(Integer page, Integer size, String id);

	/**
	 * 用户代理升级
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<AgenLevel> userAgentUpgrade(String id);

	/**
	 * 保存代理申请账户
	 * 
	 * @param user
	 * @return
	 */
	public MsgDto<String> addAgent(SysUser user, String currentSysUserId);

	/**
	 * 显示所有代理信息
	 * 
	 * @param page
	 * @param size
	 * @param code
	 * @return
	 */
	public MsgDto<Page<User>> findAgentInfo(Integer page, Integer size, String code, String nickName,Integer isActive);

	/**
	 * 获取进入game websocket 的token
	 * 
	 * @return
	 */
	public MsgDto<String> getTokenBySysUser(String sysUserId);

	/**
	 * 获取用户信息
	 * 
	 * @param token
	 * @return
	 */
	public MsgDto<SysUser> getSysUserByToken(String token);

	/**
	 * 显示个人充值金额
	 * 
	 * @param code
	 * @return
	 */
	public MsgDto<User> findPersonalRechargeAmount(String code);

	/**
	 * 显示下级代理充值金额
	 * 
	 * @param page
	 * @param size
	 * @param code
	 * @return
	 */
	public MsgDto<Page<User>> findSubagentRechargeAmount(Integer page, Integer size, String code);

	/**
	 * 设置代理账号状态
	 * 
	 * @param code
	 * @return
	 */
	public MsgDto<Integer> settingAccountStatus(String code);

	/**
	 * 修改代理等级
	 * 
	 * @param code
	 * @param levelId
	 * @return
	 */
	public MsgDto<Integer> updateAgentLevel(String code, String levelId);

	/**
	 * 刷新客服session过期时间
	 * 
	 * @param token
	 */
	public void refreshSysToken(String token);

	/**
	 * 获取当前登录用户的游戏信息
	 * 
	 * @param id
	 * @return
	 */
	public MsgDto<User> getGameUserInfo(String id);

	/**
	 * 获取用户余额
	 * 
	 * @param sysUser
	 * @return
	 */
	public BigDecimal getUserBalance(String sysUserId);

	/**
	 * 初始化用户密码
	 * 
	 * @param id
	 * @return
	 */
	public Integer initUserPassword(String id);

	/**
	 * 通过账号查询用户
	 * 
	 * @param account
	 * @return
	 */
	public SysUser getAccountUser(String account);

	/**
	 * 获取代理账号
	 * 
	 * @return
	 */
	public List<SysUser> getAgenUser();

	/**
	 * 代理推广费提现
	 * 
	 * @param sysUserId
	 * @param cash
	 * @param zhifubaoAccount
	 * @param zhifubaoName
	 * @param phone
	 * @param yzCode
	 * @return
	 */
	public MsgDto<String> agentGetCash(String sysUserId, Double cash, String zhifubaoAccount, String zhifubaoName,
			String phone, String yzCode);

	/**
	 * 玩家金币提现
	 * 
	 * @param sysUserId
	 * @param cash
	 * @param zhifubaoAccount
	 * @param zhifubaoName
	 * @param phone
	 * @param yzCode
	 * @return
	 */
	public MsgDto<String> goldGetCash(String sysUserId, Double cash, String zhifubaoAccount, String zhifubaoName,
			String phone, String yzCode);

	/**
	 * 是否为高危用户
	 * 
	 * @param userId
	 * @return
	 */
	public MsgDto<Boolean> isDangerUser(String userId);

	/**
	 * 修改用户密码
	 * 
	 * @param sysUser
	 */
	public MsgDto<String> updateUserPassword(SysUser sysUser, String oldPassword, String password);

	/**
	 * 申请代理账号
	 * 
	 * @param token
	 * @return
	 */
	public MsgDto<String> addProxySysUser(SysUser sysUser,String token);

}
