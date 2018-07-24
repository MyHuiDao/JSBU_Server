package com.huidao.game.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.entity.AgenApply;
import com.huidao.common.entity.GameEmail;
import com.huidao.common.entity.PayLog;
import com.huidao.common.entity.SysUser;
import com.huidao.common.entity.User;
import com.huidao.common.enums.GameUserOnceType;
import com.huidao.common.enums.GoldSourceType;
import com.huidao.common.interfaces.admin.IAgenApplyService;
import com.huidao.common.interfaces.admin.IGameEmailService;
import com.huidao.common.interfaces.admin.ISysUserService;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.game.IGameUserOnceService;
import com.huidao.common.interfaces.game.IPayLogService;
import com.huidao.common.interfaces.game.IUserGoldLogService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.interfaces.game.user.IUserSafeService;
import com.huidao.common.interfaces.sms.ISmsService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.huidao.common.util.HttpUtil;
import com.huidao.game.config.SystemConfig;
import com.huidao.game.manager.UserManager;
import com.yehebl.orm.data.common.dto.Page;

@Controller
@RequestMapping("/user")
public class UserController {

	@Reference
	private IUserService userService;

	@Reference
	private IUserSafeService userSafeService;

	@Reference
	private IGameSettingService gameSettingService;

	@Reference
	private ISmsService smsService;

	@Autowired
	private SystemConfig systemConfig;

	@Reference
	private IGameEmailService gameEmailService;

	@Reference
	private IGameUserOnceService gameUserOnceService;

	@Reference
	private ISysUserService sysUserService;

	@Reference
	private IAgenApplyService agenApplyService;

	@Reference
	private IUserGoldLogService userGoldLogService;

	@Reference
	private IPayLogService payLogService;

	/**
	 * 通过设备id登录
	 * 
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value = "/loginByDeviceId", method = RequestMethod.GET)
	@ResponseBody
	public MsgDto<String> loginByDeviceId(String deviceId, HttpServletRequest request) {
		String ip = HttpUtil.getIp(request);
		return userService.loginByDeviceId(deviceId, ip);
	}

	/**
	 * 通过设备id登录
	 * 
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value = "/loginByWeixinId", method = RequestMethod.GET)
	@ResponseBody
	public MsgDto<String> loginByWeixinId(String code, HttpServletRequest request) {
		String ip = HttpUtil.getIp(request);
		return userService.loginByWeixinId(code, ip);
	}

	/**
	 * 获取当前用户信息
	 * 
	 * @param deviceId
	 * @return
	 */
	@RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
	@Permission
	@JSON(clazz = { User.class }, property = {
			"id,nickName,code,headImg,mobile,isBind,userType,experience,gold,vipLevel,isVip" })
	public MsgDto<User> getCurrentUser() {
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		return MsgFactory.success(user);
	}

	/**
	 * 修改头像
	 * 
	 * @param id
	 * @param headImg
	 * @return
	 */
	@RequestMapping(value = "/updHeadImg", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<String> updHeadImg(String id, String headImg) {
		return userService.updateHeadImg(id, headImg);
	}

	/**
	 * 获取推广链接
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getRecommendUrl", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<String> getRecommendUrl() {
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		String value = gameSettingService.getValue(GameSettingContants.recommend_url);
		return MsgFactory.success(value + "?recommendId=" + user.getId());
	}

	/**
	 * 微信跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/recommendWeixin", method = RequestMethod.GET)
	public void recommendWeixin(HttpServletResponse response, String recommendId) {
		try {
			response.sendRedirect(
					"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + systemConfig.getWeixinAppId()
							+ "&redirect_uri=" + systemConfig.getWeixinRedirectUri() + "/user/getWeixinInfo"
							+ "&response_type=code&scope=snsapi_userinfo&state=" + recommendId + "#wechat_redirect");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(value = "/getWeixinInfo", method = RequestMethod.GET)
	public void getWeixinInfo(String code, String state, HttpServletRequest request, HttpServletResponse response) {
		try {
			String ip = HttpUtil.getIp(request);
			userService.addUserByWeixinId(code, state, ip);
			response.sendRedirect(systemConfig.getGameDownLoad() + "?recommendId=" + state + "#weixin");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 用户注册
	 * 
	 * @param account
	 *            帐号名
	 * @param password
	 *            密码
	 * @return
	 */
	@RequestMapping(value = "/userRegistration", method = RequestMethod.GET)
	@JSON
	public MsgDto<String> userRegistration(HttpServletRequest request, String account, String password) {
		String ip = HttpUtil.getIp(request);
		return userService.userRegistration(ip, account, password);
	}

	/**
	 * 用户登录
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/loginByUser", method = RequestMethod.GET)
	@ResponseBody
	@JSON
	public MsgDto<String> loginByUser(String account, String password, HttpServletRequest request) {
		String ip = HttpUtil.getIp(request);
		return userService.loginByUser(account, password, ip);
	}

	/**
	 * 用户保险柜存入功能
	 * 
	 * @param gold
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/userSafe", method = RequestMethod.POST)
	@Permission
	@JSON
	public MsgDto<String> userSafe(Integer gold) {
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		return userSafeService.userSafeIn(user.getId(), gold);
	}

	/**
	 * 保险柜取出功能
	 * 
	 * @param gold
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/userTakeOut", method = RequestMethod.POST)
	@Permission
	@JSON
	public MsgDto<String> userTakeOut(Integer gold) {
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		return userSafeService.userTakeOut(user.getId(), gold);
	}

	/**
	 * 保险柜获取金币
	 * 
	 * @param gold
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/userSafeGet", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<Integer> userSafeGet() {
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		return userSafeService.userSafeGet(user.getId());
	}

	@RequestMapping(value = "/userMobileBind", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<String> userMobileBind(String mobile, String code) {
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		MsgDto<String> msg = smsService.validateCode(mobile, code);
		if (msg.getCode().equals(MsgFactory.success_code)) {
			return userService.bindMobile(user.getId(), mobile);
		}
		return msg;
	}

	@RequestMapping(value = "/userMobileSendMsg", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<String> userMobileSendMsg(String mobile) {
		smsService.sendValidateCode(mobile, systemConfig.getValidateCodeTime());
		return MsgFactory.successMsg("发送验证码成功");
	}

	@RequestMapping(value = "/userMobileUnBind", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<String> userMobileUnBind(String mobile, String code) {
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		MsgDto<String> msg = smsService.validateCode(mobile, code);
		if (msg.getCode().equals(MsgFactory.success_code)) {
			return userService.unBindMobile(user.getId(), mobile);
		}
		return msg;

	}

	@RequestMapping(value = "/getUserGameEmail", method = RequestMethod.POST)
	@Permission
	@JSON
	public MsgDto<Page<GameEmail>> getUserGameEmail(Integer page, Integer size) {
		User user = userService.getUserByToken(UserManager.getCurrentToken());

		return gameEmailService.getUserGameEmail(page, size, user.getId());
	}

	@RequestMapping(value = "/isGameUserOnce", method = RequestMethod.GET)
	@Permission
	@JSON
	public MsgDto<Boolean> isGameUserOnce() {
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		return gameUserOnceService.isGameUserOnce(user.getId(), GameUserOnceType.once_pay);
	}

	@RequestMapping(value = "/getCash", method = RequestMethod.POST)
	@JSON
	@Permission
	public MsgDto<String> getCash(Integer cash, String zhifubaoAccount, String zhifubaoName, String yzCode,
			Integer type) {
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		if (StringUtils.isBlank(user.getWeixinId())) {
			return MsgFactory.failMsg("游客用户不能提现");
		}
		SysUser sysUser = sysUserService.getGameUserId(user.getId());
		if (sysUser == null) {
			String token = UUID.randomUUID().toString();
			RedisCache.set(CacheContants.token_user_id + token, user.getId(), 10 * 60);
			return MsgFactory.fail("-2", systemConfig.getProxyRegister() + "?token=" + token);
		}
		AgenApply agenApply = agenApplyService.getAgenApplyType(user.getId(), 0);
		if (agenApply != null) {
			return MsgFactory.failMsg("账户审核种，请5分钟后再提现");
		}
		// 人民币兑换金币汇率
		Integer rate = Integer.valueOf(gameSettingService.getValue(GameSettingContants.gold_exchange_rate));
		return sysUserService.goldGetCash(sysUser.getId(),
				BigDecimal.valueOf(cash).divide(BigDecimal.valueOf(rate)).doubleValue(), zhifubaoAccount, zhifubaoName,
				user.getMobile(), yzCode);
	}

	@RequestMapping("/getUserGoldBalance")
	@Permission
	@JSON
	public MsgDto<String> getUserGoldBalance() {
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		if (StringUtils.isBlank(user.getWeixinId())) {
			return MsgFactory.failMsg("游客用户不能提现");
		}
		SysUser sysUser = sysUserService.getGameUserId(user.getId());
		if (sysUser == null) {
			String token = UUID.randomUUID().toString();
			RedisCache.set(CacheContants.token_user_id + token, user.getId(), 10 * 60);
			return MsgFactory.fail("-2", systemConfig.getProxyRegister() + "?token=" + token);
		}
		AgenApply agenApply = agenApplyService.getAgenApplyType(user.getId(), 0);
		if (agenApply != null) {
			return MsgFactory.failMsg("账户审核种，请5分钟后再提现");
		}
		// 人民币兑换金币汇率
		Integer rate = Integer.valueOf(gameSettingService.getValue(GameSettingContants.gold_exchange_rate));
		BigDecimal leftMoney = BigDecimal
				.valueOf(Double.valueOf(gameSettingService.getValue(GameSettingContants.game_left_money)));
		BigDecimal winGold1 = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.win);// 赢的
		BigDecimal getCashGold1 = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.getCash);// 提现的
		BigDecimal buyGold1 = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.buy);// 兑换礼品的
		BigDecimal loseGold1 = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.lose);// 输掉的金币
		BigDecimal minGetCash1 = BigDecimal
				.valueOf(Double.valueOf(gameSettingService.getValue(GameSettingContants.game_get_cash_add)));// 需要赢的总利润大于该值
		BigDecimal winSum = winGold1.subtract(loseGold1).subtract(buyGold1).subtract(getCashGold1);

		BigDecimal allowGetCash = BigDecimal.valueOf(0);// 允许可提现额度
		if (winSum.compareTo(minGetCash1) != -1) {
			// 赢的总额超过minGetCash1 则全部可提现
			allowGetCash = winSum.divide(BigDecimal.valueOf(rate)).subtract(leftMoney);// 计算所有赢的可提现余额
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
			allowGetCash = allowGetCash.divide(BigDecimal.valueOf(rate)).subtract(leftMoney);// 计算所有赢的可提现余额
		}

		if (allowGetCash.doubleValue() < 0) {
			allowGetCash = BigDecimal.ZERO;
		}
		return MsgFactory.success(allowGetCash.multiply(BigDecimal.valueOf(rate)).toString());
	}

	@RequestMapping("/getRegisterLink")
	@JSON
	public MsgDto<String> getRegisterLink() {
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		if (StringUtils.isBlank(user.getWeixinId())) {
			return MsgFactory.failMsg("游客用户不能提现");
		}
		SysUser sysUser = sysUserService.getGameUserId(user.getId());
		if (sysUser == null) {
			String token = UUID.randomUUID().toString();
			RedisCache.set(CacheContants.token_user_id + token, user.getId(), 10 * 60);
			return MsgFactory.success(systemConfig.getProxyRegister() + "?token=" + token);
		}
		return MsgFactory.failMsg("该用户已注册");
	}

}