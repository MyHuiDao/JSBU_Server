package com.huidao.admin.web.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.admin.web.config.SystemConfig;
import com.huidao.admin.web.contants.SessionContants;
import com.huidao.admin.web.manager.SessionManager;
import com.huidao.admin.web.manager.SysUserManager;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.anno.ValidateCode;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.dto.MyPromotion;
import com.huidao.common.entity.AgenLevel;
import com.huidao.common.entity.GameSetting;
import com.huidao.common.entity.PayLog;
import com.huidao.common.entity.SysPermission;
import com.huidao.common.entity.SysUser;
import com.huidao.common.entity.SysUserRole;
import com.huidao.common.entity.User;
import com.huidao.common.enums.GameUserOnceType;
import com.huidao.common.enums.GoldSourceType;
import com.huidao.common.enums.SysUserType;
import com.huidao.common.exception.BusinessException;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IPermissionService;
import com.huidao.common.interfaces.admin.IRoleService;
import com.huidao.common.interfaces.admin.ISysUserRoleService;
import com.huidao.common.interfaces.admin.ISysUserService;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.game.IGameUserOnceService;
import com.huidao.common.interfaces.game.IPayLogService;
import com.huidao.common.interfaces.game.IUserGoldLogService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.interfaces.sms.ISmsService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.huidao.common.util.CodeUtil;
import com.huidao.common.util.HttpUtil;
import com.huidao.common.util.QrCodeUtil;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

/**
 * 用户类控制层
 * 
 * @author lenovo
 *
 */
@Controller
@RequestMapping("/sysUser")
public class UserController {

	@Reference
	private ISysUserService sysUserService;

	@Reference
	private ISysUserRoleService sysUserRoleService;

	@Reference
	private IPermissionService permissionService;

	@Reference
	private IRoleService roleService;

	@Reference
	private IUserService userService;

	@Reference
	private IGameSettingService gameSettingService;

	@Autowired
	private SystemConfig systemConfig;

	@Reference
	private ISmsService smsService;

	@Reference
	private IUserGoldLogService userGoldLogService;

	@Reference
	private IGameUserOnceService gameUserOnceService;

	@Reference
	private IPayLogService payLogService;

	/**
	 * 获取用户数据，分页显示
	 * 
	 * @param account
	 *            用户账号
	 * @param nickName
	 *            昵称
	 * @param userType
	 *            玩家类型
	 * @return
	 */
	@RequestMapping(value = "/getUserData", method = RequestMethod.GET)
	@Permission("system_user")
	@JSON
	public MsgDto<Page<SysUser>> getUserData(String code, String nickName) {
		return sysUserService.findPage(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(), code, nickName);
	}

	/**
	 * 通过id获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findUserData", method = RequestMethod.GET)
	@Permission("user_view")
	@JSON
	public MsgDto<SysUser> findUserData(String id) {
		return sysUserService.get(id);
	}

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	@Permission("user_new")
	@JSON
	public MsgDto<String> saveUser(SysUser sysUser) {
		SysUser sysUser2 = SysUserManager.getSysUser();
		return sysUserService.add(sysUser, sysUser2.getId());
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@Permission("user_update")
	@JSON
	public MsgDto<String> updateUser(SysUser sysUser) {
		SysUser sysUser2 = SysUserManager.getSysUser();
		return sysUserService.update(sysUser, sysUser2.getId());
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 *            用户id
	 * @return
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	@Permission("user_delete")
	@JSON
	public MsgDto<String> deleteUser(String id) {
		return sysUserService.delete(id);
	}

	/**
	 * 设置系统用户、角色
	 * 
	 * @param userId
	 * @param roles
	 * @return
	 */
	@RequestMapping(value = "/setSysUserRoles", method = RequestMethod.POST)
	@Permission
	@JSON
	public MsgDto<String> setSysUserRoles(String userId, List<String> roles) {
		return sysUserService.setSysUserRoles(userId, roles);
	}

	/**
	 * 获取用户拥有权限
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getPermissionByUserId", method = RequestMethod.GET)
	@Permission
	@JSON
	public List<SysPermission> getPermissionByUserId(String userId) {
		return sysUserService.getPermissionByUserId(userId);
	}

	/**
	 * 通过用户id获取角色信息
	 * 
	 * @param sysUserId
	 * @return
	 */
	@RequestMapping(value = "/getSysUserRole", method = RequestMethod.GET)
	@Permission("user_setting_user_role")
	@JSON
	public MsgDto<List<SysUserRole>> getSysUserRole(String sysUserId) {
		return MsgFactory.success(sysUserRoleService.getSysUserRole(sysUserId));
	}

	/**
	 * 删除用户角色信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteSysUserRole", method = RequestMethod.GET)
	@Permission("user_setting_user_role_delete")
	@JSON
	public MsgDto<String> deleteSysUserRole(String id) {
		return sysUserRoleService.delete(id);
	}

	/**
	 * 将相关数据保存到中间表中
	 * 
	 * @param sysUserRole
	 * @return
	 */
	@RequestMapping(value = "/addSysUserRole", method = RequestMethod.POST)
	@Permission("user_setting_user_role_add")
	@JSON
	public MsgDto<String> addSysUserRole(SysUserRole sysUserRole) {
		return sysUserRoleService.add(sysUserRole);
	}

	/**
	 * 用户登录
	 * 
	 * @param account
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	@ValidateCode
	@JSON
	public MsgDto<SysUser> userLogin(String account, String password, HttpServletRequest request) {
		HttpSession session = request.getSession();

		// 获取ip地址
		String ip = HttpUtil.getIp(request);

		// 1.调用接口判断用户名密码是否正确
		MsgDto<SysUser> msgDto = sysUserService.login(account, password, ip);

		// 2.不正确直接返回
		if (!msgDto.getCode().equals(MsgFactory.success_code)) {
			return msgDto;
		}

		// 3.正确将用户信息保存到session中
		session.setAttribute(SessionContants.USER_LOGIN_KEY, msgDto.getData());

		// 4.并获取用户所拥有的权限，将codes集合存储到session中
		Set<String> codes = new HashSet<String>();
		Set<String> codes1 = new HashSet<String>();

		// 5.获取当前用户数据
		SysUser sysUser = msgDto.getData();

		if (SessionManager.map.containsKey(msgDto.getData().getId())) {
			HttpSession httpSession = SessionManager.map.get(msgDto.getData().getId());
			if (!httpSession.getId().equals(session.getId())) {
				try {
					SessionManager.map.get(msgDto.getData().getId()).setAttribute("offline", "true");
				} catch (Exception e) {
				}
			}
		}

		// 6.获取所有权限
		MsgDto<List<SysPermission>> msgDto2 = permissionService.findAll();
		// 判断用户是否为超级管理员，若是配置所有权限
		if (sysUser.getId().equals("1")) {
			for (SysPermission sp : msgDto2.getData()) {
				codes1.add(sp.getCode());
			}
			SessionManager.map.put(msgDto.getData().getId(), session);
			return msgDto;
		}

		// 7.获取用户所拥有的权限
		List<SysPermission> lists = permissionService.getPermissionCode(sysUser.getId());
		for (SysPermission sp : lists) {
			codes1.add(sp.getCode());
		}

		// 8.将所有权限保存到list集合中
		List<SysPermission> listAll = msgDto2.getData();
		for (SysPermission sp : listAll) {
			if (!codes1.contains(sp.getCode())) {
				codes.add(sp.getCode());
			}
		}

		session.setAttribute(SessionContants.USER_PERMISSION_NOT_CODE, codes);
		session.setAttribute(SessionContants.USER_PERMISSION_CODE, codes1);
		SessionManager.map.put(msgDto.getData().getId(), session);
		return msgDto;
	}

	/**
	 * 获取用户权限code
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getPermissionCodes", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<Set<String>> getPermissionCodes() {
		return MsgFactory.success(SysUserManager.getNotPermission());
	}

	/**
	 * 判断用户旧密码是否正确
	 * 
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/validatePassword", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<Boolean> validatePassword(String password) {
		// 1.获取用户信息
		SysUser sysUser = SysUserManager.getSysUser();
		// 2.进行旧密码还原
		String oldPassword = CodeUtil.md5Encode(sysUser.getSalt() + password);
		// 3.对比密码是否正确
		if (!oldPassword.equals(sysUser.getPassword())) {
			return MsgFactory.success(false);
		}
		return MsgFactory.success(true);

	}

	/**
	 * 用户修改密码
	 * 
	 * @param oldPassword
	 * @param password
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/modifyPassWord", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<String> modifyPassWord(String oldPassword, String password) {
		if (StringUtils.isBlank(oldPassword)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(password)) {
			throw ParamException.param_not_exception;
		}
		// 1.获取用户信息
		SysUser sysUser = SysUserManager.getSysUser();
		return sysUserService.updateUserPassword(sysUser, oldPassword, password);
	}

	/**
	 * 用户退出
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<String> logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(SessionContants.USER_LOGIN_KEY);
		return MsgFactory.success();
	}

	/**
	 * 获取游戏用户信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findUserAll", method = RequestMethod.GET)
	@Permission("game_user_list")
	@JSON(clazz = { User.class }, filterProperty = { "salt,weixinId,deviceId,unionId" })
	public MsgDto<Page<User>> findUserAll(String code, String nickName, String account) {
		SysUser sysUser = SysUserManager.getSysUser();
		return userService.findUserAll(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(), code, nickName,
				account, sysUser.getType(), sysUser.getGameUserId());
	}

	/**
	 * 获取用户余额
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findUserBalance", method = RequestMethod.GET)
	@JSON
	public MsgDto<BigDecimal> findUserBalance() {
		SysUser sysUser = SysUserManager.getSysUser();
		if (sysUser.getType().equals(SysUserType.admin.toString())) {
			return MsgFactory.success();
		}
		return MsgFactory.success(sysUserService.getUserBalance(sysUser.getId()));
	}

	@RequestMapping(value = "/getCash", method = RequestMethod.POST)
	@JSON
	@Permission
	public MsgDto<String> getCash(Double cash, String zhifubaoAccount, String zhifubaoName, String yzCode,
			Integer type) {
		SysUser sysUser = SysUserManager.getSysUser();
		User user = userService.getUserById(sysUser.getGameUserId());
		if (type == 0) {
			return sysUserService.agentGetCash(sysUser.getId(), cash, zhifubaoAccount, zhifubaoName, user.getMobile(),
					yzCode);
		} else {
			return sysUserService.goldGetCash(sysUser.getId(), cash, zhifubaoAccount, zhifubaoName, user.getMobile(),
					yzCode);
		}
	}

	@RequestMapping("/personalCenter")
	@Permission
	@JSON
	public MsgDto<AgenLevel> personalCenter() {
		SysUser sysUser = SysUserManager.getSysUser();
		return sysUserService.personalCenter(sysUser.getId());
	}

	@RequestMapping("/findMyPromotionAll")
	@Permission("agent_my_promotion")
	@JSON
	public MsgDto<Page<MyPromotion>> findMyPromotionAll() {
		SysUser sysUser = SysUserManager.getSysUser();
		return sysUserService.findMyPromotion(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(),
				sysUser.getGameUserId());
	}

	@RequestMapping("/findCountMoney")
	@JSON
	public MsgDto<BigDecimal> findCountMoney() {
		SysUser sysUser = SysUserManager.getSysUser();
		return sysUserService.findCountMoney(sysUser.getGameUserId());
	}

	@RequestMapping(value = "/getQrCode", method = RequestMethod.GET)
	@Permission
	public void getQrCode(HttpServletResponse response) {
		SysUser sysUser = SysUserManager.getSysUser();
		if (StringUtils.isBlank(sysUser.getGameUserId())) {
			throw new BusinessException("该用户没有游戏用户");
		}
		User user = userService.getUserById(sysUser.getGameUserId());
		if (user == null) {
			throw ParamException.param_exception;
		}
		String value = gameSettingService.getValue(GameSettingContants.recommend_url);
		response.setContentType("image/jpeg");
		response.setHeader("Content-Disposition", "attachment;filename=jinshayugang.png");
		QrCodeUtil.getQrCode(300, 300, value + "?recommendId=" + user.getId(),
				systemConfig.getQrCodeShow() + File.separator + "logo.png", response);
	}

	@RequestMapping(value = "/getQrCodeShow", method = RequestMethod.GET)
	@Permission("agent_material_download")
	public void getQrCode(HttpServletResponse response, String name) {
		SysUser sysUser = SysUserManager.getSysUser();
		if (StringUtils.isBlank(sysUser.getGameUserId())) {
			throw new BusinessException("该用户没有游戏用户");
		}
		User user = userService.getUserById(sysUser.getGameUserId());
		if (user == null) {
			throw ParamException.param_exception;
		}
		String value = gameSettingService.getValue(GameSettingContants.recommend_url);
		response.setHeader("Content-Type", "application/x-jpg");
		response.setHeader("Content-Disposition", "attachment;filename=" + user.getNickName() + "_" + name);
		QrCodeUtil.getQrCodeShow(value + "?recommendId=" + user.getId(),
				systemConfig.getQrCodeShow() + File.separator + name,
				systemConfig.getQrCodeShow() + File.separator + "logo.png", response);
	}

	@RequestMapping("/findMySubagent")
	@Permission("agent_my_subagent")
	@JSON
	public MsgDto<Page<MyPromotion>> findMySubagent() {
		SysUser sysUser = SysUserManager.getSysUser();
		return sysUserService.findMySubagent(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(),
				sysUser.getGameUserId());
	}

	@RequestMapping("/userAgentUpgrade")
	@Permission("user_agent_upgrade")
	@JSON
	public MsgDto<AgenLevel> userAgentUpgrade() {
		SysUser sysUser = SysUserManager.getSysUser();
		return sysUserService.userAgentUpgrade(sysUser.getId());
	}

	@RequestMapping("/findAgentInfo")
	@JSON
	public MsgDto<Page<User>> findAgentInfo(String code, String nickName,Integer isActive) {

		return sysUserService.findAgentInfo(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(), code, nickName,isActive);
	}

	@RequestMapping("/getToken")
	@JSON
	@Permission("customer_service_authority")
	public MsgDto<String> getToken() {
		SysUser sysUser = SysUserManager.getSysUser();
		return sysUserService.getTokenBySysUser(sysUser.getId());
	}

	@RequestMapping("/findPersonalRechargeAmount")
	@JSON
	public MsgDto<User> findPersonalRechargeAmount(String code) {

		return sysUserService.findPersonalRechargeAmount(code);
	}

	@RequestMapping("/findSubagentRechargeAmount")
	@JSON
	public MsgDto<Page<User>> findSubagentRechargeAmount(String code) {

		return sysUserService.findSubagentRechargeAmount(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(),
				code);
	}

	@RequestMapping("/settingAccountStatus")
	@JSON
	public MsgDto<Integer> settingAccountStatus(String code) {

		return sysUserService.settingAccountStatus(code);
	}

	@RequestMapping("/updateAgentLevel")
	@JSON
	public MsgDto<Integer> updateAgentLevel(String code, String levelId) {
		return sysUserService.updateAgentLevel(code, levelId);
	}

	@RequestMapping("/getUserInfo")
	@JSON
	public MsgDto<User> getUserInfo(String token) {
		if (StringUtils.isBlank(token)) {
			throw ParamException.param_not_exception;
		}
		User user = userService.getUserByToken(token);
		return MsgFactory.success(user);
	}

	@RequestMapping("/sendValidateCode")
	@JSON
	public MsgDto<String> sendValidateCode(String mobile, String token) {

		if (StringUtils.isBlank(token)) {
			SysUser sysUser = SysUserManager.getSysUser();
			if (sysUser == null) {
				MsgFactory.failMsg("账号未登录");
			}
		} else {
			User user = userService.getUserByToken(token);
			if (StringUtils.isNotBlank(user.getMobile())) {
				mobile = user.getMobile();
			}
		}
		smsService.sendValidateCode(mobile, 300);
		return MsgFactory.successMsg("发送验证码成功");
	}

	@RequestMapping("/validateCode")
	@JSON
	public MsgDto<String> validateCode(String mobile, String yzCode, String token) {
		MsgDto<String> validateCode = smsService.validateCode(mobile, yzCode);
		if (MsgFactory.isSuccess(validateCode)) {
			User user = userService.getUserByToken(token);
			userService.bindMobile(user.getId(), mobile);
		}
		return validateCode;
	}

	@RequestMapping("/binDingPhone")
	@JSON
	public MsgDto<String> binDingPhone(String code, String phone) {
		if (StringUtils.isBlank(code)) {
			throw ParamException.param_not_exception;
		}
		User user = userService.getUserCode(code);
		return userService.bindMobile(user.getId(), phone);
	}

	@RequestMapping("/getGameUserInfo")
	@JSON
	public MsgDto<User> getGameUserInfo() {
		SysUser sysUser = SysUserManager.getSysUser();
		return sysUserService.getGameUserInfo(sysUser.getId());
	}

	@RequestMapping("/getUserCanWithdraw")
	@Permission
	@JSON
	public MsgDto<BigDecimal> getUserCanWithdraw() {
		SysUser sysUser = SysUserManager.getSysUser();
		User user = userService.getUserById(sysUser.getGameUserId());
		// 获取用户总金额
		BigDecimal balance = sysUserService.getUserBalance(sysUser.getId());

		// 1.获取赢的金币
		BigDecimal win = userGoldLogService.getSumGoldByType(sysUser.getGameUserId(), GoldSourceType.win);
		// 2.获取输掉的金币
		BigDecimal lose = userGoldLogService.getSumGoldByType(sysUser.getGameUserId(), GoldSourceType.lose);
		// 3.获取总换奖品的金币
		BigDecimal buy = userGoldLogService.getSumGoldByType(sysUser.getGameUserId(), GoldSourceType.buy);
		// 4.获取提现的金币
		BigDecimal getCash = userGoldLogService.getSumGoldByType(sysUser.getGameUserId(), GoldSourceType.getCash);
		// 5.输的总额(金币)
		BigDecimal useItGold = getCash.add(lose.add(buy));
		// 6.贏的总额(金币)
		BigDecimal winGold = win.subtract(useItGold);
		// 7.得到需要赢的金币大于多少才能提现
		GameSetting gameSetting = gameSettingService.getKeyGameSetting(GameSettingContants.game_get_cash_add);
		GameSetting gameSetting3 = gameSettingService.getKeyGameSetting(GameSettingContants.gold_exchange_rate);
		BigDecimal rechargeGold = BigDecimal.valueOf(Long.valueOf(gameSetting.getValue()))
				.multiply(BigDecimal.valueOf(Long.valueOf(gameSetting3.getValue())));
		GameSetting gameSetting2 = gameSettingService.getKeyGameSetting(GameSettingContants.game_left_money);
		BigDecimal leftMoney = BigDecimal.valueOf(Long.valueOf(gameSetting2.getValue()));
		// 不可提现的额度
		if (winGold.doubleValue() < rechargeGold.doubleValue()) {
			// 8.将赢的金币转换为金额
			// 总金币
			BigDecimal totalAmount = BigDecimal.valueOf(user.getGold());
			// 不可提现的余额
			BigDecimal withdrawGold = totalAmount.divide(BigDecimal.valueOf(Long.valueOf(gameSetting3.getValue())));
			// 计算可提现的金额
			BigDecimal cashWithdrawal = balance.subtract(withdrawGold).subtract(leftMoney);
			if (cashWithdrawal.compareTo(BigDecimal.valueOf(Long.valueOf(0))) == -1) {
				cashWithdrawal = BigDecimal.valueOf(0);
			}
			return MsgFactory.successMsg("可提现金额为:" + cashWithdrawal);
		} else {
			// 赢的金额减去必须剩下的金额，为可提现的金额
			BigDecimal cashWithdrawal = balance.subtract(leftMoney);
			return MsgFactory.successMsg("可提现金额为:" + cashWithdrawal);
		}
	}

	@RequestMapping("/getProxyRegister")
	@Permission("customer_service_authority,send_proxy_apply_link")
	@JSON
	public MsgDto<String> getProxyRegister(String userId) {
		String token = UUID.randomUUID().toString();
		RedisCache.set(CacheContants.token_user_id + token, userId, 10 * 60);
		return MsgFactory.success(systemConfig.getProxyRegister() + "?token=" + token);
	}

	@RequestMapping("/initUserPassword")
	@Permission("init_user_password")
	@JSON
	public MsgDto<Integer> initUserPassword(String id) {

		return MsgFactory.success(sysUserService.initUserPassword(id));
	}

	@RequestMapping(value = "/getAgentQrCode", method = RequestMethod.GET)
	@Permission
	public void getAgentQrCode(HttpServletResponse response, String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		String value = gameSettingService.getValue(GameSettingContants.recommend_url);
		response.setContentType("image/jpeg");
		response.setHeader("Content-Disposition", "attachment;filename=jinshayugang.png");
		QrCodeUtil.getQrCode(300, 300, value + "?recommendId=" + id,
				systemConfig.getQrCodeShow() + File.separator + "logo.png", response);
	}

	@RequestMapping("/getBalance")
	@Permission
	@JSON
	public MsgDto<BigDecimal> getBalance() {
		SysUser sysUser = SysUserManager.getSysUser();
		if (sysUser.getType().equals(SysUserType.admin.toString())) {
			return MsgFactory.success();
		}
		// 1.获取用户余额
		BigDecimal money = sysUser.getMoney();
		// 2.查询最低保留RMB数
		GameSetting gameSetting = gameSettingService.getKeyGameSetting(GameSettingContants.game_left_money);
		BigDecimal userMoney = money.subtract(BigDecimal.valueOf(Long.parseLong(gameSetting.getValue())));
		if (userMoney.doubleValue() < 0) {
			userMoney = BigDecimal.ZERO;
		}
		return MsgFactory.success(userMoney);
	}

	@RequestMapping("/getUserGoldBalance")
	@Permission
	@JSON
	public MsgDto<BigDecimal> getUserGoldBalance() {
		SysUser sysUser = SysUserManager.getSysUser();

		// 人民币兑换金币汇率
		Integer rate = Integer.valueOf(gameSettingService.getValue(GameSettingContants.gold_exchange_rate));

		// 获取代理用户对于的游戏用户的
		User user = userService.getUserById(sysUser.getGameUserId());

		if (user == null) {
			throw ParamException.param_exception;
		}

		BigDecimal leftMoney = BigDecimal
				.valueOf(Double.valueOf(gameSettingService.getValue(GameSettingContants.game_left_money)));
		BigDecimal winGold1 = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.win);// 赢的
		BigDecimal getCashGold1 = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.getCash);// 提现的
		BigDecimal buyGold1 = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.buy);// 兑换礼品的
		BigDecimal loseGold1 = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.lose);// 输掉的金币
		BigDecimal payGold1 = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.pay);// 充值的金币
		BigDecimal minGetCash1 = BigDecimal
				.valueOf(Double.valueOf(gameSettingService.getValue(GameSettingContants.game_get_cash_add)));// 需要赢的总利润大于该值
		BigDecimal winSum = winGold1.subtract(loseGold1).subtract(buyGold1).subtract(getCashGold1);

		BigDecimal allowGetCash = BigDecimal.valueOf(0);// 允许可提现额度
		if (winSum.compareTo(minGetCash1) != -1) {
			// 赢的总额超过minGetCash1 则全部可提现
			allowGetCash = winSum.add(payGold1).divide(BigDecimal.valueOf(rate));// 计算所有赢的可提现余额
		} else {
			// 计算充值区间提现
			BigDecimal consume = null;
			if (sysUserService.isDangerUser(user.getId()).getData()) {
				consume = BigDecimal.valueOf(
						Double.valueOf(gameSettingService.getValue(GameSettingContants.user_danger_consume_gold)));
			} else {
				consume = BigDecimal
						.valueOf(Double.valueOf(gameSettingService.getValue(GameSettingContants.user_consume_gold)));
			}

			List<PayLog> userGoldLogList = payLogService.getPayGold(user.getId());
			BigDecimal full = BigDecimal.ZERO;
			int index = 0;
			int sumIndex = 0;
			for (int i = 0; i < userGoldLogList.size(); i++) {
				BigDecimal consumeGoldTarget = BigDecimal.valueOf(userGoldLogList.get(i).getGold())
						.divide(BigDecimal.valueOf(100)).multiply(consume);// 消费目标
				if (index > i && full.abs().doubleValue() >= consumeGoldTarget.doubleValue()) {
					allowGetCash = allowGetCash
							.add(BigDecimal.valueOf(userGoldLogList.get(i).getGold()).subtract(consumeGoldTarget));
					full = full.abs().subtract(consumeGoldTarget);
					sumIndex = i;
					continue;
				} else if (index <= i) {
					full = BigDecimal.ZERO;
				}
				// 第几次提现
				for (int j = index; j < userGoldLogList.size(); j++) {
					Date startDate = userGoldLogList.get(j).getCreateDate();
					Date endDate = null;
					if (userGoldLogList.size() > j + 1) {
						endDate = userGoldLogList.get(j + 1).getCreateDate();
					} else {
						endDate = new Date();
					}
					BigDecimal winGold = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.win,
							startDate, endDate);// 赢的
					BigDecimal loseGold = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.lose,
							startDate, endDate);// 输掉的金币
					BigDecimal consumeGold = winGold.subtract(loseGold).subtract(full);
					if (consumeGold.doubleValue() >= 0) {
						continue;
					}
					// 消费的大于目标值
					if (consumeGold.abs().doubleValue() >= consumeGoldTarget.doubleValue()) {
						allowGetCash = allowGetCash
								.add(BigDecimal.valueOf(userGoldLogList.get(i).getGold()).subtract(consumeGoldTarget));
						BigDecimal many = consumeGold.abs().subtract(consumeGoldTarget); // 消耗金币比目标消耗金币还多，下次计算金币当前的下一次开始
						sumIndex = i;
						if (many.doubleValue() >= 0) {
							index = j + 1;
							full = many;
							break;
						}
					} else {
						// 消费小于目标值 还差多少够提现
						full = consumeGold.abs();
						index++;
					}

				}
			}
			Long sumPayLog = 0L;
			if (sumIndex > 0) {
				for (int j = 0; j <= sumIndex; j++) {
					sumPayLog += userGoldLogList.get(j).getGold();
				}
				if (winSum.doubleValue() <= 0) {
					BigDecimal left = BigDecimal.valueOf(sumPayLog).subtract(winSum.abs());
					if (left.doubleValue() <= allowGetCash.doubleValue()) {
						allowGetCash = left;
					}
				}
			}
			allowGetCash = allowGetCash.divide(BigDecimal.valueOf(rate));// 计算所有赢的可提现余额
		}
		if (gameUserOnceService.isGameUserOnce(user.getId(), GameUserOnceType.once_get_cash).getData()) {
			Double firstGetCash = Double.valueOf(gameSettingService.getValue(GameSettingContants.game_onec_get_cash));
			if (allowGetCash.doubleValue() < leftMoney.doubleValue() && allowGetCash.doubleValue() >= firstGetCash) {
				allowGetCash = BigDecimal.valueOf(firstGetCash);
			}
		} else {
			allowGetCash = allowGetCash.subtract(leftMoney);// 已经首提
		}
		if (allowGetCash.doubleValue() < 0) {
			allowGetCash = BigDecimal.ZERO;
		}
		return MsgFactory.success(allowGetCash);
	}

	@RequestMapping("/addProxySysUser")
	@JSON
	public MsgDto<String> addProxySysUser(SysUser sysUser, String token) {

		return sysUserService.addProxySysUser(sysUser, token);
	}
}
