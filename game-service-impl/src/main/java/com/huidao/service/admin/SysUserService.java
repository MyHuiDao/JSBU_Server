package com.huidao.service.admin;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.AlipayApiException;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.dto.MyPromotion;
import com.huidao.common.entity.AgenApply;
import com.huidao.common.entity.AgenLevel;
import com.huidao.common.entity.DangerGetCashUser;
import com.huidao.common.entity.PayLog;
import com.huidao.common.entity.SysPermission;
import com.huidao.common.entity.SysUser;
import com.huidao.common.entity.SysUserGetCash;
import com.huidao.common.entity.SysUserLogin;
import com.huidao.common.entity.SysUserRole;
import com.huidao.common.entity.User;
import com.huidao.common.enums.GameType;
import com.huidao.common.enums.GameUserOnceType;
import com.huidao.common.enums.GoldSourceType;
import com.huidao.common.enums.SysUserType;
import com.huidao.common.exception.BusinessException;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IAgenLevelService;
import com.huidao.common.interfaces.admin.ISysUserService;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.game.IGameUserOnceService;
import com.huidao.common.interfaces.game.IPayLogService;
import com.huidao.common.interfaces.game.IUserGoldLogService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.interfaces.sms.ISmsService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.huidao.common.util.AliPayUtil;
import com.huidao.common.util.CodeUtil;
import com.huidao.common.util.ValidateUtil;
import com.huidao.config.SystemConfig;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.ICopyProperties;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

@Service(timeout = 10000)
@Component
public class SysUserService implements ISysUserService {

	private static Logger log = LoggerFactory.getLogger(SysUserService.class);

	@Autowired
	private IGameSettingService gameSettingService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IUserGoldLogService userGoldLogService;

	@Autowired
	private IGameUserOnceService gameUserOnceService;

	@Autowired
	private IAgenLevelService agenLevelService;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private IPayLogService payLogService;

	@Autowired
	private DBDao dbDao;

	@Autowired
	private ISmsService smsService;

	@Override
	public MsgDto<Page<SysUser>> findPage(Integer page, Integer size, String code, String nickName) {
		Map<String, Object> maps = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(code)) {
			maps.put("code", code);
		}
		if (StringUtils.isNotBlank(nickName)) {
			maps.put("nickName", nickName);
		}
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.setParams(maps);
		return MsgFactory.success(dbDao.findPageBySqlName("findSysUserAll", queryXmlSql, page, size, SysUser.class));
	}

	@Override
	@Transactional
	public MsgDto<String> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		SysUser sysUser = dbDao.get(id, SysUser.class);
		if (sysUser == null) {
			throw ParamException.param_exception;
		}
		sysUser.setStatus(2);
		dbDao.update(sysUser);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> add(SysUser user, String currentSysUserId) {
		if (user == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(user.getAccount())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(user.getNickName())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(user.getPassword())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(currentSysUserId)) {
			throw ParamException.param_not_exception;
		}
		user.setType(SysUserType.admin.toString());
		Integer count = dbDao.getCountByExpression("EQ_account", user.getAccount(), SysUser.class);
		if (count > 0) {
			return MsgFactory.failMsg("当前用户已存在");
		}
		Date d = new Date();
		user.setUpdateDate(d);
		user.setSysUserId(currentSysUserId);
		user.setAgenLevelId(agenLevelService.findAgenLevelAll().get(0).getId().toString());
		user.setCreateDate(new Date());
		user.setSalt(UUID.randomUUID().toString());
		user.setMoney(BigDecimal.ZERO);
		user.setPassword(CodeUtil.md5Encode(user.getSalt() + user.getPassword()));
		dbDao.save(user);
		return MsgFactory.success();
	}

	@Override
	public MsgDto<SysUser> get(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.get(id, SysUser.class));
	}

	@Override
	@Transactional
	public MsgDto<String> setSysUserRoles(String userId, List<String> roles) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		if (roles == null) {
			return MsgFactory.success();
		}
		dbDao.deleteByExpression("EQ_sysUserId", userId, SysUserRole.class);
		List<SysUserRole> surList = new ArrayList<SysUserRole>();
		for (String role : roles) {
			if (StringUtils.isBlank(role)) {
				continue;
			}
			SysUserRole sur = new SysUserRole();
			sur.setRoleId(role);
			sur.setSysUserId(userId);
			surList.add(sur);
		}
		dbDao.save(surList);
		return MsgFactory.success();
	}

	@Override
	public List<SysPermission> getPermissionByUserId(String userId) {
		QueryXmlSql qx = new QueryXmlSql();
		qx.addParams("sysUserId", userId);
		return dbDao.findBySqlName("getPermissionsByUserId", qx, SysPermission.class);
	}

	@Override
	public MsgDto<SysUser> login(String account, String password, String ip) {

		if (StringUtils.isBlank(account)) {
			throw ParamException.param_not_exception;
		}

		if (StringUtils.isBlank(password)) {
			throw ParamException.param_not_exception;
		}

		SysUser sysUser = dbDao.getByExpression("EQ_account", account, SysUser.class);

		if (sysUser == null) {
			AgenApply aenLevel = dbDao.getByExpression("EQ_account", account, AgenApply.class);
			if (aenLevel != null) {
				return MsgFactory.failMsg("正在审核中,请5分钟后登录");
			}
			return MsgFactory.failMsg("账号或者密码输入错误");
		}

		if (!sysUser.getPassword().equals(CodeUtil.md5Encode(sysUser.getSalt() + password))) {
			return MsgFactory.failMsg("账号或者密码输入错误");
		}

		if (0 != sysUser.getStatus()) {
			return MsgFactory.failMsg("账号被禁用,请联系网站管理员");
		}

		SysUserLogin syl = new SysUserLogin();
		syl.setUserId(sysUser.getSysUserId());
		syl.setLoginTime(new Date());
		syl.setIp(ip);

		return MsgFactory.success(sysUser);
	}

	@Override
	@Transactional
	public MsgDto<String> update(SysUser user, String currentSysUserId) {
		if (user == null) {
			throw ParamException.param_not_exception;
		}
		dbDao.update(user, new ICopyProperties<SysUser>() {
			@Override
			public void copy(SysUser oldEntity, SysUser newEntity) {
				newEntity.setNickName(oldEntity.getNickName());
				newEntity.setStatus(oldEntity.getStatus());
				newEntity.setType(oldEntity.getType());
				newEntity.setUpdateDate(new Date());
				newEntity.setSysUserId(currentSysUserId);
			}
		}, SysUser.class);
		return MsgFactory.success();
	}

	/**
	 * 通过用户游戏id查询用户
	 */
	@Override
	public SysUser getGameUserId(String userId) {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("EQ_gameUserId", userId);
		return dbDao.getByMap(maps, SysUser.class);
	}

	/**
	 * 查询有使用代理级别的管理员
	 * 
	 * @param agenLevelId
	 * @return
	 */
	@Override
	public List<SysUser> getAgenLevels(String agenLevelId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_agenLevelId", agenLevelId);
		return dbDao.findByMap(map, SysUser.class);
	}

	@Override
	@Transactional
	public MsgDto<String> getCash(String sysUserId, Double cash, String zhifubaoAccount, String zhifubaoName,
			String phone, String yzCode) {
		if (StringUtils.isBlank(sysUserId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(yzCode)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(phone)) {
			throw ParamException.param_not_exception;
		}
		if (!ValidateUtil.isMobile(phone)) {
			throw ParamException.param_exception;
		}

		if (cash == null || cash == 0) {
			throw ParamException.param_not_exception;
		}
		MsgDto<String> validateCode = smsService.validateCode(phone, yzCode);
		if (MsgFactory.isFail(validateCode)) {
			return validateCode;
		}
		DecimalFormat df = new DecimalFormat("#.00");
		cash = Double.valueOf(df.format(cash));
		if (StringUtils.isBlank(zhifubaoAccount)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(zhifubaoName)) {
			throw ParamException.param_not_exception;
		}
		SysUser sysUser = dbDao.get(sysUserId, SysUser.class);
		if (sysUser == null) {
			// 没有后台用户
			throw ParamException.param_exception;
		}
		// 后台代理余额
		BigDecimal sumMoney = sysUser.getMoney();
		User user = null;
		// 人民币兑换金币汇率
		Integer rate = Integer.valueOf(gameSettingService.getValue(GameSettingContants.gold_exchange_rate));
		if (StringUtils.isNotBlank(sysUser.getGameUserId())) {
			// 获取代理用户对于的游戏用户的
			user = dbDao.get(sysUser.getGameUserId(), User.class);
			if (user == null) {
				throw ParamException.param_exception;
			}
			Long money = user.getGold() / rate;// 金币换算成人民币
			sumMoney = sumMoney.add(BigDecimal.valueOf(money)); // 计算代理余额和游戏用户金币 换算成的总额
		}
		// 判断总额是否大于提现金额
		if (sumMoney.compareTo(BigDecimal.valueOf(cash)) == -1) {
			return MsgFactory.failMsg("余额不足");
		}
		// 保存代理提现记录
		SysUserGetCash sugc = new SysUserGetCash();
		sugc.setGold(0L);
		sugc.setCreateTime(new Date());
		sugc.setSysUserId(sysUserId);
		sugc.setBalance(BigDecimal.ZERO);
		sugc.setZhifubaoAccount(zhifubaoAccount);
		sugc.setZhifubaoName(zhifubaoName);
		sugc.setMoney(BigDecimal.valueOf(cash));

		// 如果代理余额大于提现金额的话，全部在代理余额中提现
		if (sysUser.getMoney().compareTo(BigDecimal.valueOf(cash)) != -1) {
			// 是否首提
			if (gameUserOnceService.isGameUserOnce(user.getId(), GameUserOnceType.once_get_cash).getData()) {
				// 未首提
				// 首次提现要等于该数值
				Double firstGetCash = Double
						.valueOf(gameSettingService.getValue(GameSettingContants.game_onec_get_cash));
				if (!cash.equals(firstGetCash)) {
					Integer agentGetCase = Integer
							.valueOf(gameSettingService.getValue(GameSettingContants.game_agent_get_case));
					int result = (int) (cash % agentGetCase);
					if (result != 0) {
						throw new BusinessException("提现需要是" + agentGetCase + "的整数倍");
					}
				}
				gameUserOnceService.addGameUserOnce(user.getId(), GameUserOnceType.once_get_cash);
			} else {
				Integer agentGetCase = Integer
						.valueOf(gameSettingService.getValue(GameSettingContants.game_agent_get_case));
				int result = (int) (cash % agentGetCase);
				if (result != 0) {
					throw new BusinessException("提现需要是" + agentGetCase + "的整数倍");
				}
			}
			int update = dbDao.update("update sys_user set money=money-? where  money-?>=0 and id=?", SysUser.class,
					cash, cash, sysUserId);
			if (update <= 0) {
				throw new BusinessException("余额不足");
			}
			// 提现记录扣除的余额
			sugc.setBalance(BigDecimal.valueOf(cash));
			// 代理余额和金币余额大于提现总额
		} else if (sumMoney.compareTo(BigDecimal.valueOf(cash)) != -1) {
			// 先减去代理余额
			int update = dbDao.update("update sys_user set money=money-? where money-?>=0 and id=? ", SysUser.class,
					sysUser.getMoney(), sysUser.getMoney(), sysUserId);
			if (update <= 0) {
				throw new BusinessException("余额不足");
			}
			// 提现记录扣除的余额
			sugc.setBalance(sysUser.getMoney());
			if (user == null) {
				// 如果没有游戏用户了,则余额不足
				throw new BusinessException("余额不足");
			}
			// 获取 换算成金币( 提现金额-代理余额) 还需要在游戏账户中扣除 gold 金币
			BigDecimal gold = BigDecimal.valueOf(cash).subtract(sysUser.getMoney()).multiply(BigDecimal.valueOf(rate));
			// 游戏账户中最少要剩余多少人民币
			Double leftMoney = Double.valueOf(gameSettingService.getValue(GameSettingContants.game_left_money));
			if (gameUserOnceService.isGameUserOnce(user.getId(), GameUserOnceType.once_get_cash).getData()) {
				// 未首提
				Double firstGetCash = Double
						.valueOf(gameSettingService.getValue(GameSettingContants.game_onec_get_cash));
				if (!cash.equals(firstGetCash)) {
					Integer agentGetCase = Integer
							.valueOf(gameSettingService.getValue(GameSettingContants.game_agent_get_case));
					int result = (int) (cash % agentGetCase);
					if (result != 0) {
						throw new BusinessException("提现需要是" + agentGetCase + "的整数倍");
					}
					BigDecimal winGold = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.win);// 赢的
					BigDecimal getCashGold = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.getCash);// 提现的
					BigDecimal buyGold = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.buy);// 兑换礼品的
					BigDecimal loseGold = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.lose);// 输掉的金币
					Double minGetCash = Double
							.valueOf(gameSettingService.getValue(GameSettingContants.game_get_cash_add));// 需要赢的总利润大于该值
					// 计算最小提现金额
					BigDecimal minGetCash1 = BigDecimal.valueOf(minGetCash)
							.add(BigDecimal.valueOf(leftMoney).multiply(BigDecimal.valueOf(rate)));
					if ((winGold.subtract(loseGold).subtract(getCashGold).subtract(buyGold))
							.compareTo(minGetCash1) == -1) {
						throw new BusinessException("提现必须高于" + minGetCash1 + "元");
					}
				}
				gameUserOnceService.addGameUserOnce(user.getId(), GameUserOnceType.once_get_cash);
			} else {
				// 扣除的金币-换算成金币(必须要剩余的人民币)
				if (gold.subtract(BigDecimal.valueOf(leftMoney).multiply(BigDecimal.valueOf(rate))).intValue() < 0) {
					throw new BusinessException("账户内最少剩余" + leftMoney + "元");
				}
				Integer agentGetCase = Integer
						.valueOf(gameSettingService.getValue(GameSettingContants.game_agent_get_case));
				int result = (int) (cash % agentGetCase);
				if (result != 0) {
					throw new BusinessException("提现需要是" + agentGetCase + "的整数倍");
				}
				BigDecimal winGold = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.win);// 赢的
				BigDecimal getCashGold = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.getCash);// 提现的
				BigDecimal buyGold = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.buy);// 兑换礼品的
				BigDecimal loseGold = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.lose);// 输掉的金币
				Double minGetCash = Double.valueOf(gameSettingService.getValue(GameSettingContants.game_get_cash_add));// 需要赢的总利润大于该值
				// 计算最小提现金额
				BigDecimal minGetCash1 = BigDecimal.valueOf(minGetCash)
						.add(BigDecimal.valueOf(leftMoney).multiply(BigDecimal.valueOf(rate)));
				if ((winGold.subtract(loseGold).subtract(getCashGold).subtract(buyGold)).compareTo(minGetCash1) == -1) {
					throw new BusinessException("提现必须高于" + minGetCash1 + "元");
				}
			}
			MsgDto<String> reduceGold = userService.reduceGold(user.getId(), gold.intValue(), GoldSourceType.getCash,
					GameType.admin, null);
			if (!MsgFactory.isSuccess(reduceGold)) {
				throw new BusinessException("余额不足");
			}
			sugc.setGold(gold.longValue());
		} else {
			throw new BusinessException("余额不足");
		}
		dbDao.save(sugc);
		try {
			boolean flag = AliPayUtil.getCash(zhifubaoAccount, zhifubaoName, cash);
			if (!flag) {
				throw new BusinessException("提现失败，请联系管理员");
			}
		} catch (AlipayApiException e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("调用支付宝出错，请联系管理员");
		}
		return MsgFactory.successMsg("提现成功");
	}

	@Override
	public MsgDto<AgenLevel> personalCenter(String id) {
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("id", id);
		return MsgFactory.success(dbDao.getBySqlName("personalCenter", queryXmlSql, AgenLevel.class));
	}

	@Override
	public MsgDto<Page<MyPromotion>> findMyPromotion(Integer page, Integer size, String id) {
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("id", id);
		return MsgFactory
				.success(dbDao.findPageBySqlName("findMyPromotion", queryXmlSql, page, size, MyPromotion.class));
	}

	@Override
	public MsgDto<BigDecimal> findCountMoney(String id) {

		return MsgFactory.success(dbDao.getResultObjectBySql(
				"select SUM(abl.bonus_money) money from agent_bonus_log abl  left join pay_log pl on pl.id=abl.pay_id left join `user` u on u.id=pl.user_id where u.recommend_user_id=?",
				id).bigDecimalValue());
	}

	@Override
	public MsgDto<Page<MyPromotion>> findMySubagent(Integer page, Integer size, String id) {
		// 1.查询当前我有的代理人数
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("id", id);
		Page<MyPromotion> list = dbDao.findPageBySqlName("findMyPromotion2", queryXmlSql, page, size,
				MyPromotion.class);
		return MsgFactory.success(list);
	}

	@Override
	public MsgDto<AgenLevel> userAgentUpgrade(String id) {
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("id", id);
		return MsgFactory.success(dbDao.getBySqlName("userAgentUpgrade", queryXmlSql, AgenLevel.class));
	}

	@Override
	@Transactional
	public MsgDto<String> addAgent(SysUser user, String currentSysUserId) {
		if (user == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(user.getAccount())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(user.getNickName())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(user.getPassword())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(currentSysUserId)) {
			throw ParamException.param_not_exception;
		}
		Integer count = dbDao.getCountByExpression("EQ_account", user.getAccount(), SysUser.class);
		if (count > 0) {
			return MsgFactory.failMsg("当前用户已存在");
		}
		Date d = new Date();
		user.setUpdateDate(d);
		user.setSysUserId(currentSysUserId);
		user.setAgenLevelId(agenLevelService.findAgenLevelAll().get(0).getId().toString());
		user.setCreateDate(new Date());
		user.setMoney(BigDecimal.ZERO);
		user.setPassword(user.getPassword());
		dbDao.save(user);
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setSysUserId(user.getId());
		sysUserRole.setRoleId(SysUserType.agen.toString());
		dbDao.save(sysUserRole);
		return MsgFactory.success();
	}

	@Override
	public MsgDto<Page<User>> findAgentInfo(Integer page, Integer size, String code, String nickName,Integer isActive) {
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		if (StringUtils.isNotBlank(code)) {
			queryXmlSql.addParams("code", code);
		}
		if (StringUtils.isNotBlank(nickName)) {
			queryXmlSql.addParams("nickName", "%" + nickName + "%");
		}
		if(isActive==null||isActive==0) {
			return MsgFactory.success(dbDao.findPageBySqlName("findAgentInfo", queryXmlSql, page, size, User.class));
		}
		return MsgFactory.success(dbDao.findPageBySqlName("findAgentInfo1", queryXmlSql, page, size, User.class));		
	}

	@Override
	public MsgDto<String> getTokenBySysUser(String sysUserId) {
		String token = UUID.randomUUID().toString();
		RedisCache.set(CacheContants.token_sysUser_custmoter_service + token, sysUserId);
		return MsgFactory.success(token);
	}

	@Override
	public MsgDto<SysUser> getSysUserByToken(String token) {
		String sysUserId = RedisCache.get(CacheContants.token_sysUser_custmoter_service + token);
		if (StringUtils.isBlank(sysUserId)) {
			return MsgFactory.fail();
		}
		return get(sysUserId);
	}

	@Override
	public void refreshSysToken(String token) {
		RedisCache.resetTime(CacheContants.token_sysUser_custmoter_service + token, systemConfig.getTokenTime());
	}

	@Override
	public MsgDto<User> findPersonalRechargeAmount(String code) {
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("code", code);
		return MsgFactory.success(dbDao.getBySqlName("findPersonalRechargeAmount", queryXmlSql, User.class));
	}

	@Override
	public MsgDto<Page<User>> findSubagentRechargeAmount(Integer page, Integer size, String code) {
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("code", code);
		return MsgFactory
				.success(dbDao.findPageBySqlName("findSubagentRechargeAmount", queryXmlSql, page, size, User.class));
	}

	@Override
	@Transactional
	public MsgDto<Integer> settingAccountStatus(String code) {
		if (StringUtils.isBlank(code)) {
			throw ParamException.param_not_exception;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_code", code);
		User user = dbDao.getByMap(map, User.class);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("EQ_gameUserId", user.getId());
		SysUser sysUser = dbDao.getByMap(map2, SysUser.class);
		if (sysUser != null) {
			if (sysUser.getStatus() == 0) {
				sysUser.setStatus(1);
			} else {
				sysUser.setStatus(0);
			}
		}

		return MsgFactory.success(dbDao.update(sysUser));
	}

	@Override
	@Transactional
	public MsgDto<Integer> updateAgentLevel(String code, String levelId) {
		if (StringUtils.isBlank(levelId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(code)) {
			throw ParamException.param_not_exception;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_code", code);
		User user = dbDao.getByMap(map, User.class);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("EQ_gameUserId", user.getId());
		SysUser sysUser = dbDao.getByMap(map2, SysUser.class);
		if (sysUser != null) {
			sysUser.setAgenLevelId(levelId);
		}
		return MsgFactory.success(dbDao.update(sysUser));
	}

	@Override
	public MsgDto<User> getGameUserInfo(String id) {
		SysUser sysUser = dbDao.get(id, SysUser.class);
		if (sysUser != null) {
			User user = userService.getUserById(sysUser.getGameUserId());
			if (user != null) {
				return MsgFactory.success(user);
			}
		}
		return null;
	}

	@Override
	public BigDecimal getUserBalance(String sysUserId) {
		SysUser sysUser = dbDao.get(sysUserId, SysUser.class);
		// 9.获取用户余额
		BigDecimal money = sysUser.getMoney();
		// 获取游戏金币转换成余额
		User user = userService.getUserById(sysUser.getGameUserId());
		String rate = gameSettingService.getValue(GameSettingContants.gold_exchange_rate);
		BigDecimal conversion = BigDecimal.valueOf(user.getGold()).divide(BigDecimal.valueOf(Long.valueOf(rate)));
		BigDecimal balance = money.add(conversion);
		return balance;
	}

	@Override
	public Integer initUserPassword(String id) {
		SysUser sysUser = dbDao.get(id, SysUser.class);
		sysUser.setSalt(UUID.randomUUID().toString());
		sysUser.setPassword(CodeUtil.md5Encode(sysUser.getSalt() + "888888"));
		return dbDao.update(sysUser);
	}

	@Override
	public SysUser getAccountUser(String account) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_account", account);
		return dbDao.getByMap(map, SysUser.class);
	}

	@Override
	public List<SysUser> getAgenUser() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MONTH, -1);// 当前时间前去一个月，即一个月前的时间
		calendar.getTime();// 获取一年前的时间，或者一个月前的时间
		return dbDao.createExpressionMap().eq("type", SysUserType.agen.toString()).lt("createDate", calendar.getTime())
				.eq("status", "0").find(SysUser.class);
	}

	@Override
	@Transactional
	public MsgDto<String> agentGetCash(String sysUserId, Double cash, String zhifubaoAccount, String zhifubaoName,
			String phone, String yzCode) {
		if (StringUtils.isBlank(sysUserId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(yzCode)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(phone)) {
			throw ParamException.param_not_exception;
		}
		if (!ValidateUtil.isMobile(phone)) {
			throw ParamException.param_exception;
		}

		if (cash == null || cash == 0) {
			throw ParamException.param_not_exception;
		}
		MsgDto<String> validateCode = smsService.validateCode(phone, yzCode);
		if (MsgFactory.isFail(validateCode)) {
			return validateCode;
		}
		DecimalFormat df = new DecimalFormat("#.00");
		cash = Double.valueOf(df.format(cash));
		if (StringUtils.isBlank(zhifubaoAccount)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(zhifubaoName)) {
			throw ParamException.param_not_exception;
		}
		SysUser sysUser = dbDao.get(sysUserId, SysUser.class);
		if (sysUser == null) {
			// 没有后台用户
			throw ParamException.param_exception;
		}
		if (StringUtils.isBlank(sysUser.getGameUserId())) {
			// 没有游戏用户
			throw ParamException.param_exception;
		}
		// 后台代理余额
		BigDecimal sumMoney = sysUser.getMoney();
		User user = userService.getUserById(sysUser.getGameUserId());
		if (user == null) {
			throw ParamException.param_exception;
		}
		// 判断总额是否大于提现金额
		if (sumMoney.compareTo(BigDecimal.valueOf(cash)) == -1) {
			return MsgFactory.failMsg("余额不足");
		}
		// 保存代理提现记录
		SysUserGetCash sugc = new SysUserGetCash();
		sugc.setGold(0L);
		sugc.setCreateTime(new Date());
		sugc.setSysUserId(sysUserId);
		sugc.setBalance(BigDecimal.ZERO);
		sugc.setZhifubaoAccount(zhifubaoAccount);
		sugc.setZhifubaoName(zhifubaoName);
		sugc.setMoney(BigDecimal.valueOf(cash));
		sugc.setType(2);
		// 如果代理余额大于提现金额的话，全部在代理余额中提现
		if (sysUser.getMoney().compareTo(BigDecimal.valueOf(cash)) != -1) {
			// 是否首提
			if (gameUserOnceService.isGameUserOnce(user.getId(), GameUserOnceType.once_get_cash).getData()) {
				// 未首提
				// 首次提现要等于该数值
				Double firstGetCash = Double
						.valueOf(gameSettingService.getValue(GameSettingContants.game_onec_get_cash));
				if (!cash.equals(firstGetCash)) {
					Integer agentGetCase = Integer
							.valueOf(gameSettingService.getValue(GameSettingContants.game_agent_get_case));
					int result = (int) (cash % agentGetCase);
					if (result != 0) {
						throw new BusinessException("提现需要是" + agentGetCase + "的整数倍");
					}
				}
				gameUserOnceService.addGameUserOnce(user.getId(), GameUserOnceType.once_get_cash);
			} else {
				Integer agentGetCase = Integer
						.valueOf(gameSettingService.getValue(GameSettingContants.game_agent_get_case));
				int result = (int) (cash % agentGetCase);
				if (result != 0) {
					throw new BusinessException("提现需要是" + agentGetCase + "的整数倍");
				}
			}
			int update = dbDao.update("update sys_user set money=money-? where  money-?>=0 and id=?", SysUser.class,
					cash, cash, sysUserId);
			if (update <= 0) {
				throw new BusinessException("余额不足");
			}
			// 提现记录扣除的余额
			sugc.setBalance(BigDecimal.valueOf(cash));
			// 代理余额和金币余额大于提现总额
		} else {
			throw new BusinessException("余额不足");
		}
		dbDao.save(sugc);
		try {
			boolean flag = AliPayUtil.getCash(zhifubaoAccount, zhifubaoName, cash);
			if (!flag) {
				throw new BusinessException("提现失败，请联系管理员");
			}
		} catch (AlipayApiException e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("调用支付宝出错，请联系管理员");
		}
		return MsgFactory.successMsg("提现成功");
	}

	@Override
	@Transactional
	public MsgDto<String> goldGetCash(String sysUserId, Double cash, String zhifubaoAccount, String zhifubaoName,
			String phone, String yzCode) {
		if (StringUtils.isBlank(sysUserId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(yzCode)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(phone)) {
			throw ParamException.param_not_exception;
		}
		if (!ValidateUtil.isMobile(phone)) {
			throw ParamException.param_exception;
		}

		if (cash == null || cash == 0) {
			throw ParamException.param_not_exception;
		}
		MsgDto<String> validateCode = smsService.validateCode(phone, yzCode);
		if (MsgFactory.isFail(validateCode)) {
			return validateCode;
		}
		DecimalFormat df = new DecimalFormat("#.00");
		cash = Double.valueOf(df.format(cash));
		if (StringUtils.isBlank(zhifubaoAccount)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(zhifubaoName)) {
			throw ParamException.param_not_exception;
		}
		SysUser sysUser = dbDao.get(sysUserId, SysUser.class);
		if (sysUser == null) {
			// 没有后台用户
			throw ParamException.param_exception;
		}
		// 人民币兑换金币汇率
		Integer rate = Integer.valueOf(gameSettingService.getValue(GameSettingContants.gold_exchange_rate));
		// 获取代理用户对于的游戏用户的
		User user = userService.getUserById(sysUser.getGameUserId());
		if (user == null) {
			throw ParamException.param_exception;
		}
		BigDecimal sumMoney = BigDecimal.valueOf(user.getGold()).divide(BigDecimal.valueOf(rate));// 金币换算成人民币
		// 判断总额是否大于提现金额
		if (sumMoney.compareTo(BigDecimal.valueOf(cash)) == -1) {
			return MsgFactory.failMsg("余额不足");
		}
		// 保存代理提现记录
		SysUserGetCash sugc = new SysUserGetCash();
		sugc.setGold(0L);
		sugc.setCreateTime(new Date());
		sugc.setSysUserId(sysUserId);
		sugc.setStatus(0);
		sugc.setBalance(BigDecimal.ZERO);
		sugc.setZhifubaoAccount(zhifubaoAccount);
		sugc.setZhifubaoName(zhifubaoName);
		sugc.setMoney(BigDecimal.valueOf(cash));
		sugc.setType(0);
		BigDecimal leftMoney = BigDecimal.ZERO;
		leftMoney = BigDecimal
				.valueOf(Double.valueOf(gameSettingService.getValue(GameSettingContants.game_left_money)));
		BigDecimal winGold1 = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.win);// 赢的
		BigDecimal getCashGold1 = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.getCash);// 提现的
		BigDecimal buyGold1 = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.buy);// 兑换礼品的
		BigDecimal loseGold1 = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.lose);// 输掉的金币
		BigDecimal payGold1 = userGoldLogService.getSumGoldByType(user.getId(), GoldSourceType.pay);// 充值的金币
		BigDecimal minGetCash1 = BigDecimal
				.valueOf(Double.valueOf(gameSettingService.getValue(GameSettingContants.game_get_cash_add)));// 需要赢的总利润大于该值
		BigDecimal winSum = winGold1.subtract(loseGold1).subtract(buyGold1).subtract(getCashGold1);

		BigDecimal allowGetCash = BigDecimal.ZERO;// 允许可提现额度
		if (winSum.compareTo(minGetCash1) != -1) {
			sugc.setType(0);
			// 赢的总额超过minGetCash1 则全部可提现
			allowGetCash = winSum.add(payGold1).divide(BigDecimal.valueOf(rate));// 计算所有赢的可提现余额
		} else {
			sugc.setType(1);
			// 计算充值区间提现
			BigDecimal consume = null;
			if (isDangerUser(user.getId()).getData()) {
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

		if (allowGetCash.compareTo(BigDecimal.valueOf(cash)) != -1) {
			if (gameUserOnceService.isGameUserOnce(user.getId(), GameUserOnceType.once_get_cash).getData()) {
				// 未首提
				Double firstGetCash = Double
						.valueOf(gameSettingService.getValue(GameSettingContants.game_onec_get_cash));
				if (!cash.equals(firstGetCash)) {
					allowGetCash = allowGetCash.subtract(leftMoney);// 计算所有赢的可提现余额
					if (allowGetCash.compareTo(BigDecimal.valueOf(cash)) == -1) {
						throw new BusinessException("余额不足");
					}
					Integer agentGetCase = Integer
							.valueOf(gameSettingService.getValue(GameSettingContants.game_agent_get_case));
					int result = (int) (cash % agentGetCase);
					if (result != 0) {
						throw new BusinessException("提现需要是" + agentGetCase + "的整数倍");
					}
				}
				gameUserOnceService.addGameUserOnce(user.getId(), GameUserOnceType.once_get_cash);
			} else {
				allowGetCash = allowGetCash.subtract(leftMoney);// 计算所有赢的可提现余额
				if (allowGetCash.compareTo(BigDecimal.valueOf(cash)) == -1) {
					throw new BusinessException("余额不足");
				}
				Integer agentGetCase = Integer
						.valueOf(gameSettingService.getValue(GameSettingContants.game_agent_get_case));
				int result = (int) (cash % agentGetCase);
				if (result != 0) {
					throw new BusinessException("提现需要是" + agentGetCase + "的整数倍");
				}
			}
			BigDecimal gold = BigDecimal.valueOf(cash).multiply(BigDecimal.valueOf(rate));
			MsgDto<String> reduceGold = userService.reduceGold(user.getId(), gold.intValue(), GoldSourceType.getCash,
					GameType.admin, null);
			if (!MsgFactory.isSuccess(reduceGold)) {
				throw new BusinessException("余额不足");
			}
			sugc.setGold(gold.longValue());
		} else {
			throw new BusinessException("余额不足");
		}
		dbDao.save(sugc);
		try {
			boolean flag = AliPayUtil.getCash(zhifubaoAccount, zhifubaoName, cash);
			if (!flag) {
				throw new BusinessException("提现失败，请联系管理员");
			}
		} catch (AlipayApiException e) {
			log.error(e.getMessage(), e);
			throw new BusinessException("调用支付宝出错，请联系管理员");
		}
		return MsgFactory.successMsg("提现成功");
	}

	@Override
	@Transactional
	public MsgDto<Boolean> isDangerUser(String userId) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		SysUser sysUser = dbDao.getByExpression("EQ_gameUserId", userId, SysUser.class);
		DangerGetCashUser dangerUser = dbDao.createExpressionMap().eq("status", 0).eq("userId", userId)
				.getOne(DangerGetCashUser.class);
		if (dangerUser != null) {
			return MsgFactory.success(true);
		}

		// 获取配置的规则
		String[] ruleArray = gameSettingService.getValue(GameSettingContants.user_danger_consume_rule).split(",");
		// 获取用户充值支付记录，支付记录顺序排序
		List<PayLog> payLogList = dbDao.createExpressionMap().eq("userId", userId).eq("type", 0).eq("status", 1)
				.order("createDate").find(PayLog.class);
		for (int j = 0; j < ruleArray.length; j++) {
			String[] rules = ruleArray[j].split("-");
			Double hourtime = Double.valueOf(rules[0]);// 多少小时内
			Double starRatio = Double.valueOf(rules[1]);// 开始消费比例
			Double endRatio = Double.valueOf(rules[2]); // 结束消费比例
			Integer count = Integer.valueOf(rules[3]); // 满足次数
			Date gtDate = null;// 小时数等于0时，该条件失效
			if (hourtime > 0) {
				gtDate = new Date((long) (System.currentTimeMillis() - hourtime * 60 * 60 * 1000));
			}
			// 查询用户提现记录，按充值时间倒序排列
			List<SysUserGetCash> list = dbDao.createExpressionMap().eq("sysUserId", sysUser.getId()).eq("status", 0)
					.orderDesc("createTime").gt("createTime", gtDate).find(SysUserGetCash.class);

			// 用户提现记录小鱼判断满足次数，直接进入下一规则判断
			if (list.size() < count) {
				continue;
			}
			// 判断满足次数的充值是否为输钱充值
			boolean typeFlag = false;
			for (int i = 0; i < count; i++) {
				if (list.get(i).getType() != 1) {
					typeFlag = true;
				}
			}
			// 如果不是输钱充值，则直接进入下一规则判断
			if (typeFlag) {
				continue;
			}
			// 判断每次提现的金币是否在每次充值的金币的消费区间内
			boolean flag = true;
			for (int i = 0; i < payLogList.size(); i++) {
				PayLog pl = payLogList.get(i);
				double startConsume = BigDecimal.valueOf(pl.getGold() + pl.getFreeGold())
						.divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(starRatio)).doubleValue();
				double endConsume = BigDecimal.valueOf(pl.getGold() + pl.getFreeGold()).divide(BigDecimal.valueOf(100))
						.multiply(BigDecimal.valueOf(endRatio)).doubleValue();
				// 第一次提现消费在startConsume-endConsume 之间
				if (list.size() > i) {
					long getCashGold = list.get(i).getGold();
					if (getCashGold < startConsume && getCashGold > endConsume) {
						flag = false;
					}
				}
			}
			if (flag) {
				dangerUser = new DangerGetCashUser();
				dangerUser.setCreateDate(new Date());
				dangerUser.setStatus(0);
				dangerUser.setUserId(userId);
				dbDao.save(dangerUser);
				return MsgFactory.success(true);
			}
		}

		return MsgFactory.success(false);
	}

	@Override
	@Transactional
	public MsgDto<String> updateUserPassword(SysUser sysUser, String oldPassword, String password) {
		// 2.进行旧密码还原
		String usedPassword = CodeUtil.md5Encode(sysUser.getSalt() + oldPassword);
		// 3.对比旧密码是否正确
		if (!usedPassword.equals(sysUser.getPassword())) {
			return MsgFactory.failMsg("旧密码不正确");
		}
		// 4.设置新的密码
		sysUser.setPassword(CodeUtil.md5Encode(sysUser.getSalt() + password));
		Integer mark = dbDao.update(sysUser);
		if (mark < 1) {
			return MsgFactory.failMsg("修改出错!");
		}
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> addProxySysUser(SysUser sysUser, String token) {
		User user = userService.getUserByToken(token);
		if (user == null) {
			throw ParamException.param_not_exception;
		}
		if(StringUtils.isNotBlank(user.getDeviceId())) {
			return MsgFactory.failMsg("游客不允许注册代理账号");
		}
		if (StringUtils.isBlank(sysUser.getAccount())) {
			throw ParamException.param_not_exception;
		}
		sysUser.setAccount(sysUser.getAccount().trim());
		if (StringUtils.isBlank(sysUser.getPassword())) {
			throw ParamException.param_not_exception;
		}
		SysUser ifSysUser = dbDao.createExpressionMap().eq("gameUserId", user.getId()).get(SysUser.class);
		if (ifSysUser != null) {
			return MsgFactory.failMsg("当前用户已存在");
		}
		Integer count = dbDao.getCountByExpression("EQ_account", user.getAccount(), SysUser.class);
		if (count > 0) {
			return MsgFactory.failMsg("当前用户已存在");
		}
		Long freeGold = Long.valueOf(gameSettingService.getValue(GameSettingContants.register_agent_free_gold));
		if(freeGold>0) {
			userService.addGold(user.getId(), freeGold, GoldSourceType.free, GameType.app, sysUser.getId());
		}
		sysUser.setSalt(user.getSalt());
		sysUser.setNickName(user.getNickName());
		sysUser.setUpdateDate(new Date());
		sysUser.setAgenLevelId(agenLevelService.findAgenLevelAll().get(0).getId().toString());
		sysUser.setCreateDate(new Date());
		sysUser.setMoney(BigDecimal.ZERO);
		sysUser.setPassword(CodeUtil.md5Encode(user.getSalt() + sysUser.getPassword()));
		sysUser.setGameUserId(user.getId());
		sysUser.setSysUserId(user.getId());
		sysUser.setType(SysUserType.agen.toString());
		sysUser.setStatus(0);
		dbDao.save(sysUser);
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setSysUserId(sysUser.getId());
		sysUserRole.setRoleId(SysUserType.agen.toString());
		dbDao.save(sysUserRole);
		return MsgFactory.success();
	}

}
