package com.huidao.service.game.user;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import com.alibaba.fastjson.JSONObject;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.dto.HttpResponseDto;
import com.huidao.common.entity.AgenLevel;
import com.huidao.common.entity.PayLog;
import com.huidao.common.entity.SysUser;
import com.huidao.common.entity.User;
import com.huidao.common.entity.UserGoldLog;
import com.huidao.common.entity.UserLogin;
import com.huidao.common.enums.GameType;
import com.huidao.common.enums.GameUserOnceType;
import com.huidao.common.enums.GoldSourceType;
import com.huidao.common.enums.SysUserType;
import com.huidao.common.exception.BusinessException;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IAgenLevelService;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.game.IGameUserOnceService;
import com.huidao.common.interfaces.game.IPayLogService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.huidao.common.util.CodeUtil;
import com.huidao.common.util.HttpUtil;
import com.huidao.common.util.ValidateUtil;
import com.huidao.config.SystemConfig;
import com.huidao.dto.WxUserInfoDto;
import com.huidao.enums.GameUserType;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

@Component
@Service
public class UserService implements IUserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private IdAutoService idAutoService;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private IGameSettingService gameSettingService;

	@Autowired
	private IPayLogService payLogService;

	@Autowired
	private IAgenLevelService agenbLevelService;

	@Autowired
	private IGameUserOnceService gameUserOnceService;

	@Autowired
	private DBDao dbDao;

	private void update(User user) {
		dbDao.update(user);
		RedisCache.remove(CacheContants.user + user.getId());
	}

	@Override
	public User getUserById(String id) {
		User user = dbDao.get(id, User.class);
		return user;
	}

	@Override
	@Transactional
	public MsgDto<String> addGold(String userId, long gold, GoldSourceType goldSourceType, String desc,
			GameType gameType, String sysUserId) {
		try {
			if (StringUtils.isBlank(userId)) {
				return MsgFactory.failMsg("用户id不能为空");
			}
			if (gold < 0) {
				return MsgFactory.failMsg("增加的金币不能为负数");
			}
			RedisCache.lock(CacheContants.user_gold_lock + userId);
			User user = getUserById(userId);
			String sql = "update user set gold=gold+? where id=?";
			int result = dbDao.update(sql, User.class, gold, userId);
			if (result == 0) {
				throw new BusinessException("添加金币出错");
			}
			UserGoldLog ugl = new UserGoldLog();
			if (StringUtils.isNotBlank(desc)) {
				ugl.setDesc(goldSourceType.getDesc() + desc);
			} else {
				ugl.setDesc(goldSourceType.getDesc());
			}
			ugl.setSourceType(goldSourceType.getType());
			ugl.setGoldNum(gold);
			ugl.setGoldAgo(user.getGold());
			ugl.setType(0);
			ugl.setUserId(userId);
			ugl.setCreateDate(new Date());
			ugl.setGoldAfter(user.getGold() + gold);
			ugl.setGameType(gameType.toString());
			ugl.setSysUserId(sysUserId);
			dbDao.save(ugl);
			RedisCache.remove(CacheContants.user + userId);
			return MsgFactory.success();
		} finally {
			RedisCache.unlock(CacheContants.user_gold_lock + userId);
		}
	}

	@Override
	public MsgDto<String> addGold(String userId, long gold, GoldSourceType goldSourceType, GameType gameType,
			String sysUserId) {
		return addGold(userId, gold, goldSourceType, null, gameType, sysUserId);
	}

	@Override
	@Transactional
	public MsgDto<String> reduceGold(String userId, long gold, GoldSourceType goldSourceType, String desc,
			GameType gameType, String sysUserId) {
		try {
			if (StringUtils.isBlank(userId)) {
				return MsgFactory.failMsg("用户id不能为空");
			}
			if (gold <0) {
				return MsgFactory.failMsg("减少的金币不能为正数");
			}
			RedisCache.lock(CacheContants.user_gold_lock + userId);
			User user = getUserById(userId);
			String sql = "update user set gold=gold-? where id=? and gold-?>=0";
			int result = dbDao.update(sql, User.class, gold, userId, gold);
			if (result == 0) {
				throw new BusinessException("扣除金币出错");
			}
			UserGoldLog ugl = new UserGoldLog();
			if (StringUtils.isNotBlank(desc)) {
				ugl.setDesc(goldSourceType.getDesc() + desc);
			} else {
				ugl.setDesc(goldSourceType.getDesc());
			}
			ugl.setCreateDate(new Date());
			ugl.setSourceType(goldSourceType.getType());
			ugl.setGoldNum(gold);
			ugl.setGoldAgo(user.getGold());
			ugl.setType(1);
			ugl.setUserId(userId);
			ugl.setGameType(gameType.toString());
			ugl.setGoldAfter(user.getGold() - gold);
			dbDao.save(ugl);
			RedisCache.remove(CacheContants.user + userId);
			return MsgFactory.success();
		} finally {
			RedisCache.unlock(CacheContants.user_gold_lock + userId);
		}
	}

	@Override
	public MsgDto<String> reduceGold(String userId, long gold, GoldSourceType goldSourceType, GameType gameType,
			String sysUserId) {
		return reduceGold(userId, gold, goldSourceType, null, gameType, sysUserId);
	}

	@Override
	@Transactional
	public MsgDto<String> loginByDeviceId(String deviceId, String ip) {
		if (StringUtils.isBlank(deviceId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(ip)) {
			throw ParamException.param_not_exception;
		}
		// 查询设备id
		User user = dbDao.getByExpression("EQ_deviceId", deviceId, User.class);
		Date date = new Date();
		if (user == null) {
			Long experience = Long.valueOf(gameSettingService.getValue(GameSettingContants.init_experience_num));
			int code = idAutoService.getId();
			user = new User();
			user.setNickName("游客" + code);
			user.setUserState("0");
			user.setCode(code);
			user.setSalt(UUID.randomUUID().toString());
			user.setUserType(GameUserType.free.toString());
			user.setExperience(experience);
			user.setSex("1");
			user.setIsBind("0");
			user.setVipLevel(0);
			user.setHeadImg("head_01");
			user.setIsVip("0");
			user.setGold(0L);
			user.setDeviceId(deviceId);
			user.setLockGold(0L);
			user.setCreateTime(date);
			user.setCreateIp(ip);
			dbDao.save(user);
		}
		// 当用户状态为1时，返回账户被禁用
		if ("1".equals(user.getUserState())) {
			return MsgFactory.failMsg("账户被禁用");
		}
		Long experience = Long.valueOf(gameSettingService.getValue(GameSettingContants.init_experience_num));
		if (user.getExperience() <= experience) {
			user.setExperience(experience);
			update(user);
		}
		UserLogin userLogin = new UserLogin();
		userLogin.setUserId(user.getId());
		userLogin.setIp(ip);
		userLogin.setLoginTime(date);
		dbDao.save(userLogin);
		String oldToken = RedisCache.get(CacheContants.user_id_token + user.getId());
		if (oldToken != null) {
			RedisCache.remove(CacheContants.user_id_token + user.getId());
			RedisCache.remove(CacheContants.token_user_id + oldToken);
		}
		String token = UUID.randomUUID().toString();
		RedisCache.set(CacheContants.user_id_token + user.getId(), token);
		RedisCache.set(CacheContants.token_user_id + token, user.getId());
		return MsgFactory.success(token);
	}

	private WxUserInfoDto getWeiUser(String code) {
		String http = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + systemConfig.getWeixinAppId()
				+ "&secret=" + systemConfig.getWeixinAppSecret() + "&code=" + code + "&grant_type=authorization_code";
		String resultStr = HttpUtil.get(http).getBody();
		log.info("微信http:{}", resultStr);
		JSONObject json = JSONObject.parseObject(resultStr);
		String token = json.getString("access_token");
		String openId = json.getString("openid");
		if (StringUtils.isBlank(openId)) {
			return null;
		}
		String http1 = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openId;
		HttpResponseDto httpResponseDto = HttpUtil.get(http1);
		log.info("微信http:{}", httpResponseDto.getBody());
		JSONObject json1 = JSONObject.parseObject(httpResponseDto.getBody());
		WxUserInfoDto wu = new WxUserInfoDto();
		wu.setId(json1.getString("openid"));
		wu.setName(json1.getString("nickname"));
		wu.setSex(json1.getString("sex"));
		wu.setHeadImg(json1.getString("headimgurl"));
		wu.setUnionid(json.getString("unionid"));
		if (StringUtils.isBlank(wu.getId())) {
			return null;
		}
		return wu;
	}

	private WxUserInfoDto getPublicWeiUser(String code) {
		String http = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + systemConfig.getWeixinPublicAppId()
				+ "&secret=" + systemConfig.getWeixinPublicSecret() + "&code=" + code
				+ "&grant_type=authorization_code";
		String resultStr = HttpUtil.get(http).getBody();
		log.info("微信http:{}", resultStr);
		JSONObject json = JSONObject.parseObject(resultStr);
		String token = json.getString("access_token");
		String openId = json.getString("openid");
		if (StringUtils.isBlank(openId)) {
			return null;
		}
		String http1 = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openId;
		HttpResponseDto httpResponseDto = HttpUtil.get(http1);
		log.info("微信http:{}", httpResponseDto.getBody());
		JSONObject json1 = JSONObject.parseObject(httpResponseDto.getBody());
		WxUserInfoDto wu = new WxUserInfoDto();
		wu.setId(json1.getString("openid"));
		wu.setName(json1.getString("nickname"));
		wu.setSex(json1.getString("sex"));
		wu.setHeadImg(json1.getString("headimgurl"));
		wu.setUnionid(json.getString("unionid"));
		if (StringUtils.isBlank(wu.getId())) {
			return null;
		}
		return wu;
	}

	@Override
	@Transactional
	public MsgDto<String> loginByWeixinId(String code, String ip) {
		if (StringUtils.isBlank(code)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(ip)) {
			throw ParamException.param_not_exception;
		}
		WxUserInfoDto weiUser = getWeiUser(code);
		if (weiUser == null || StringUtils.isBlank(weiUser.getUnionid())) {
			throw new BusinessException("获取微信用户信息失败");
		}
		// 查询设备id
		User user = dbDao.getByExpression("EQ_unionId", weiUser.getUnionid(), User.class);
		Date date = new Date();
		if (user == null) {
			Long experience = Long.valueOf(gameSettingService.getValue(GameSettingContants.init_experience_num));
			int code1 = idAutoService.getId();
			user = new User();
			user.setNickName(weiUser.getName());
			user.setUserState("0");
			user.setCode(code1);
			user.setSalt(UUID.randomUUID().toString());
			user.setUserType(GameUserType.free.toString());
			user.setExperience(experience);
			user.setSex(weiUser.getSex());
			user.setIsBind("0");
			user.setVipLevel(0);
			user.setHeadImg(weiUser.getHeadImg());
			user.setIsVip("0");
			user.setGold(0L);
			user.setLockGold(0L);
			user.setWeixinId(weiUser.getId());
			user.setCreateTime(date);
			user.setCreateIp(ip);
			user.setUnionId(weiUser.getUnionid());
			dbDao.save(user);
		} else {
			user.setNickName(weiUser.getName());
			user.setSex(weiUser.getSex());
			user.setHeadImg(weiUser.getHeadImg());
			update(user);
		}
		// 当用户状态为1时，返回账户被禁用
		if ("1".equals(user.getUserState())) {
			return MsgFactory.failMsg("账户被禁用");
		}
		Long experience = Long.valueOf(gameSettingService.getValue(GameSettingContants.init_experience_num));
		if (user.getExperience() <= experience) {
			user.setExperience(experience);
			update(user);
		}
		UserLogin userLogin = new UserLogin();
		userLogin.setUserId(user.getId());
		userLogin.setIp(ip);
		userLogin.setLoginTime(date);
		dbDao.save(userLogin);
		String oldToken = RedisCache.get(CacheContants.user_id_token + user.getId());
		if (oldToken != null) {
			RedisCache.remove(CacheContants.user_id_token + user.getId());
			RedisCache.remove(CacheContants.token_user_id + oldToken);
		}
		String token = UUID.randomUUID().toString();
		RedisCache.set(CacheContants.user_id_token + user.getId(), token);
		RedisCache.set(CacheContants.token_user_id + token, user.getId());
		return MsgFactory.success(token);
	}

	@Override
	public void refreshTimeOut(String token) {
		if (StringUtils.isBlank(token)) {
			throw ParamException.param_not_exception;
		}
		String userId = RedisCache.get(CacheContants.token_user_id + token);
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_exception;
		}
		RedisCache.resetTime(CacheContants.user_id_token + userId, systemConfig.getTokenTime());
		RedisCache.resetTime(CacheContants.token_user_id + token, systemConfig.getTokenTime());
	}

	@Override
	public User getUserByToken(String token) {
		if (StringUtils.isBlank(token)) {
			throw ParamException.param_not_exception;
		}
		String userId = RedisCache.get(CacheContants.token_user_id + token);
		if (StringUtils.isBlank(userId)) {
			throw new BusinessException("账号未登录");
		}
		return getUserById(userId);
	}

	@Override
	@Transactional
	public MsgDto<String> loginByUser(String account, String password, String ip) {
		// 获取单条帐号条目数
		User user = dbDao.getByExpression("EQ_account", account, User.class);
		// 帐号条目数为空时，返回用户不存在
		if (user == null) {
			return MsgFactory.failMsg("用户不存在");
		}
		// 登录时的密码不匹配时，返回密码错误
		if (!user.getPassword().equals(CodeUtil.md5Encode(user.getSalt() + password))) {
			return MsgFactory.failMsg("密码错误");
		}
		// 当用户状态为1时，返回账户被禁用
		if ("1".equals(user.getUserState())) {
			return MsgFactory.failMsg("账户被禁用");
		}
		Long experience = Long.valueOf(gameSettingService.getValue(GameSettingContants.init_experience_num));
		if (user.getExperience() <= experience) {
			user.setExperience(experience);
			update(user);
		}
		UserLogin userLogin = new UserLogin();
		userLogin.setUserId(user.getId());
		userLogin.setLoginTime(new Date());
		userLogin.setIp(ip);
		dbDao.save(userLogin);
		String oldToken = RedisCache.get(CacheContants.user_id_token + user.getId());
		if (oldToken != null) {
			RedisCache.remove(CacheContants.user_id_token + user.getId());
			RedisCache.remove(CacheContants.token_user_id + oldToken);
		}
		String token = UUID.randomUUID().toString();
		RedisCache.set(CacheContants.user_id_token + user.getId(), token);
		RedisCache.set(CacheContants.token_user_id + token, user.getId());
		return MsgFactory.success(token);
	}

	@Override
	@Transactional
	public MsgDto<String> userRegistration(String ip, String account, String password) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		// 查询ip当天注册帐号个数
		Integer result = Integer.valueOf(dbDao.getObjectBySql(
				"select count(*) from user where DATE_FORMAT(create_time,'%Y-%m-%d')=? and create_ip=? ",
				dateformat.format(new Date()), ip).toString());
		Integer registered = dbDao.getCountByExpression("EQ_account", account, User.class);
		// 注册的帐号条目数大于0时,用户已注册
		if (registered > 0) {
			return MsgFactory.failMsg("用户已注册");
		}
		// 限制单个ip注册帐号的条目数大于5条
		if (result >= 5) {
			return MsgFactory.failMsg("当天创建已超过5个");
		}
		User user = new User();
		Long experience = Long.valueOf(gameSettingService.getValue(GameSettingContants.init_experience_num));
		int code = idAutoService.getId();
		user.setAccount(account);
		user.setSalt(UUID.randomUUID().toString());
		user.setPassword(CodeUtil.md5Encode(user.getSalt() + password));
		user.setNickName("注册用户" + code);
		user.setUserState("0");
		user.setCode(code);
		user.setUserType(GameUserType.free.toString());
		user.setExperience(experience);
		user.setSex("0");
		user.setIsBind("0");
		user.setVipLevel(0);
		user.setHeadImg("head_01");
		user.setIsVip("0");
		user.setGold(0L);
		user.setLockGold(0L);
		user.setCreateTime(new Date());
		user.setCreateIp(ip);
		dbDao.save(user);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> updateHeadImg(String id, String headImg) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(headImg)) {
			throw ParamException.param_not_exception;
		}
		User user = dbDao.get(id, User.class);
		if (user == null) {
			throw ParamException.param_exception;
		}
		user.setHeadImg(headImg);
		update(user);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> bindMobile(String userId, String mobile) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(mobile)) {
			throw ParamException.param_not_exception;
		}
		if (!ValidateUtil.isMobile(mobile)) {
			throw ParamException.param_exception;
		}
		User user = dbDao.get(userId, User.class);
		if (user == null) {
			throw ParamException.param_exception;
		}
		if (StringUtils.isBlank(user.getMobile()) && "0".equals(user.getIsBind())) {
			user.setMobile(mobile);
			user.setIsBind("1");
			update(user);
			return MsgFactory.successMsg("绑定成功");
		}
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> unBindMobile(String userId, String mobile) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(mobile)) {
			throw ParamException.param_not_exception;
		}
		if (!ValidateUtil.isMobile(mobile)) {
			throw ParamException.param_exception;
		}

		User user = dbDao.get(userId, User.class);
		if (user == null) {
			throw ParamException.param_exception;
		}
		user.setMobile(null);
		user.setIsBind("0");
		update(user);
		return MsgFactory.successMsg("解绑成功");
	}

	@Override
	@Transactional
	public Long lockAllGold(String userId) {
		try {
			if (StringUtils.isBlank(userId)) {
				throw ParamException.param_not_exception;
			}
			RedisCache.lock(CacheContants.user_gold_lock + userId);
			User user = getUserById(userId);
			Long gold = user.getGold();
			String sql = "update user set gold=gold-?,lock_gold=? where id=? and gold-?>=0";
			int result = dbDao.update(sql, User.class, gold, gold, userId, gold);
			if (result == 0) {
				throw new BusinessException("锁定金币出错");
			}
			log.error("用户id["+user.getId()+"]锁定金币："+gold);
			RedisCache.remove(CacheContants.user + userId);
			return gold;
		} finally {
			RedisCache.unlock(CacheContants.user_gold_lock + userId);
		}
	}

	@Override
	@Transactional
	public MsgDto<String> unLockGold(String userId, Long gold, GameType gameType) {
		User user = dbDao.get(userId, User.class);
		String sql = "update user set gold=gold+lock_gold,lock_gold=0 where id=? ";
		int update = dbDao.update(sql, User.class, userId);
		if (update != 0) {
			long result = gold - user.getLockGold();
			if (result > 0) {
				return addGold(userId, result, GoldSourceType.win, gameType, null);
			} else if (result < 0) {
				return reduceGold(userId, Math.abs(result), GoldSourceType.lose, gameType, null);
			}
		}
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> addUserByWeixinId(String code, String userId, String ip) {

		if (StringUtils.isBlank(code)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(ip)) {
			throw ParamException.param_not_exception;
		}
		WxUserInfoDto weiUser = getPublicWeiUser(code);
		if (weiUser == null || StringUtils.isBlank(weiUser.getUnionid())) {
			throw new BusinessException("获取微信用户信息失败");
		}
		// 查询设备id
		User user = dbDao.getByExpression("EQ_unionId", weiUser.getUnionid(), User.class);
		Date date = new Date();
		if (user != null) {
			return MsgFactory.failMsg("用户已存在");
		}
		Long experience = Long.valueOf(gameSettingService.getValue(GameSettingContants.init_experience_num));
		int code1 = idAutoService.getId();
		user = new User();
		user.setNickName(weiUser.getName());
		user.setUserState("0");
		user.setCode(code1);
		user.setSalt(UUID.randomUUID().toString());
		user.setUserType(GameUserType.free.toString());
		user.setExperience(experience);
		user.setSex(weiUser.getSex());
		user.setIsBind("0");
		user.setVipLevel(0);
		user.setHeadImg(weiUser.getHeadImg());
		user.setIsVip("0");
		user.setGold(0L);
		user.setLockGold(0L);
		user.setWeixinId(weiUser.getId());
		user.setCreateTime(date);
		user.setCreateIp(ip);
		user.setRecommendUserId(userId);
		user.setUnionId(weiUser.getUnionid());
		dbDao.save(user);
		return MsgFactory.successMsg("推荐成功");
	}

	/**
	 * 后台显示所有用户信息
	 */
	@Override
	public MsgDto<Page<User>> findUserAll(Integer page, Integer size, String code, String nickName, String account,
			String type, String id) {
		QueryXmlSql qx=new QueryXmlSql();
		if (StringUtils.isNotBlank(code)) {
			qx.addParams("code", code);
		}
		if (StringUtils.isNotBlank(nickName)) {
			qx.addParams("nickName", "%"+nickName+"%");
		}
		if (StringUtils.isNotBlank(account)) {
			qx.addParams("account", account);
		}
		if (SysUserType.agen.toString().equals(type)) {
			qx.addParams("recommendUserId", id);
		}
		return MsgFactory.success(dbDao.findPageBySqlName("findPageUser",qx, page, size, User.class));
	}

	/**
	 * 获得用户的推荐人数
	 */
	@Override
	public List<User> getRecommendUserNumber(String userId) {
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("userId", userId);
		return dbDao.findBySqlName("getRecommendUserNumber", queryXmlSql, User.class);
	}

	@Override
	public MsgDto<String> setExperience(String userId, Long experience) {
		if (experience < 0) {
			experience = 0L;
		}
		String sql = "update user set experience=? where id=? ";
		int update = dbDao.update(sql, User.class, experience, userId);
		if (update == 0) {
			throw new RuntimeException("设置用户体验币失败");
		}
		return MsgFactory.success();
	}

	@Override
	public User getUserCode(String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(code)) {
			throw ParamException.param_not_exception;
		}
		map.put("EQ_code", code);
		return dbDao.getByMap(map, User.class);
	}

	@Override
	@Transactional
	public MsgDto<String> payGold(String userId, Integer gold, Integer freeGold, Double money, String desc,
			String sysUserId, Integer type) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		if (gold == null) {
			throw ParamException.param_not_exception;
		}
		User u = dbDao.get(userId, User.class);
		if (u == null) {
			throw ParamException.param_exception;
		}

		PayLog pl = new PayLog();
		pl.setCreateDate(new Date());
		pl.setCreateSysUserId(sysUserId);
		pl.setFreeGold(freeGold);
		pl.setGold(gold);
		pl.setUserId(userId);
		pl.setStatus(1);
		pl.setType(type);
		pl.setPayDate(new Date());
		pl.setPrice(BigDecimal.valueOf(money));
		dbDao.save(pl);
		SysUser sysUser = dbDao.getByExpression("EQ_gameUserId", u.getId(), SysUser.class);
		if (sysUser != null) {
			AgenLevel agenLevel = agenbLevelService.get(sysUser.getAgenLevelId());
			if (agenLevel != null) {
				AgenLevel nextAgentLevel = getAgenLevelNextByMoeny(agenLevel, money);
				if (nextAgentLevel != null) {
					sysUser.setAgenLevelId(nextAgentLevel.getId());
					dbDao.update(sysUser);
				}
			}
		}
		payLogService.bouns(1, u.getRecommendUserId(), pl.getPrice(), pl.getId(), null);
		MsgDto<String> msg = addGold(pl.getUserId(), pl.getGold(), GoldSourceType.pay, desc, GameType.admin, null);
		if (MsgFactory.isFail(msg)) {
			log.error("添加金币出错{}", pl.getStatus());
			throw new RuntimeException("添加金币出错");
		}
		if (pl.getFreeGold() > 0) {
			MsgDto<String> msg1 = addGold(pl.getUserId(), pl.getFreeGold(), GoldSourceType.free, desc, GameType.admin,
					null);
			if (MsgFactory.isFail(msg1)) {
				log.error("添加赠送金币出错{}", pl.getStatus());
				throw new RuntimeException("添加金币出错");
			}
		}
		if (gameUserOnceService.isGameUserOnce(pl.getUserId(), GameUserOnceType.once_pay).getData()) {
			gameUserOnceService.addGameUserOnce(pl.getUserId(), GameUserOnceType.once_pay);
		}
		User u1 = dbDao.get(pl.getUserId(), User.class);
		u1.setIsVip("1");
		u1.setVipLevel(1);
		dbDao.update(u1);
		return MsgFactory.success("充值成功");
	}

	private AgenLevel getAgenLevelNextByMoeny(AgenLevel currentAgenLevel, Double money) {
		AgenLevel nextAgentLevel = agenbLevelService.getNextLevel(currentAgenLevel.getLevel());
		if (nextAgentLevel != null) {
			if (nextAgentLevel.getPrice().doubleValue() <= money) {
				return getAgenLevelNextByMoeny(nextAgentLevel, money);
			} else {
				return currentAgenLevel;
			}
		}
		return currentAgenLevel;
	}

}
