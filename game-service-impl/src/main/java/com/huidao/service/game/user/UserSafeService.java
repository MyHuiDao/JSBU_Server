package com.huidao.service.game.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.entity.SafeGold;
import com.huidao.common.entity.User;
import com.huidao.common.exception.BusinessException;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.interfaces.game.user.IUserSafeService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;

@Service
@Component
public class UserSafeService implements IUserSafeService {

	@Autowired
	private DBDao dbDao;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IGameSettingService gameSettingService;

	@Override
	public MsgDto<String> userSafeIn(String userId,Integer  gold) {
		if(StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		if(gold==null||gold<=0) {
			throw ParamException.param_not_exception;
		}
		Long saveGold= Long.valueOf(gameSettingService.getValue(GameSettingContants.safa_save_gold));
		if(gold%saveGold!=0) {
			return MsgFactory.failMsg("您存入的金额不是"+saveGold+"的倍数，无法取出！");
		}
		// 1.从Redis中获取用户信息
		User user = userService.getUserById(userId);
		// 2.获取用户账户的金币数
		Long user_gold = user.getGold();
		// 3.判断用户当前的金币是否大于10000
		if (user_gold < 10000) {
			return MsgFactory.failMsg("存入保险柜的游戏币数目不能少于10000，游戏币存入失败！");
		}
		// 4.通过用户id查询用户是否已在保险柜中存入金币
		SafeGold safeGold = dbDao.getBySql(
				"select sg.id,sg.gold,sg.user_id,sg.`password` from safe_gold sg where sg.user_id=?", SafeGold.class,
				user.getId());
		// 标记属性
		Integer mark = 0;
		Integer userMark = 0;
		// 5.如果保险柜中用户已存入金币
		if (safeGold == null) {
			// 6.用户未在保险柜中存入过金币，则新建一条记录
			SafeGold newSafeGold = new SafeGold();
			newSafeGold.setGold(gold);
			newSafeGold.setUserId(user.getId());
			newSafeGold.setPassword("88888888");
			mark = dbDao.save(newSafeGold);
		}
		// 7.将用户要存入的金币存入保险柜中
		mark = dbDao.update("update safe_gold sg set sg.gold=sg.gold+? where sg.user_id=?", SafeGold.class, gold,
				user.getId());
		userMark = dbDao.update("update user u set u.gold=u.gold-? where u.id=? and u.gold>=0", User.class, gold,
				user.getId());
		// 8.当修改或保存出错时，抛出异常并且回滚
		if (mark < 1 || userMark < 1) {
			throw new BusinessException("业务异常");
		}
		RedisCache.remove(CacheContants.user+userId);
		return MsgFactory.success();
	}

	@Override
	public MsgDto<String> userTakeOut(String userId,Integer gold) {
		if(StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		if(gold==null||gold<=0) {
			throw ParamException.param_not_exception;
		}
		Long saveGold= Long.valueOf(gameSettingService.getValue(GameSettingContants.safa_save_gold));
		if(gold%saveGold!=0) {
			return MsgFactory.failMsg("您取出的金额不是"+saveGold+"的倍数，无法取出！");
		}
		// 1.从Redis中取出用户信息
		User user = userService.getUserById(userId);
		// 2.读取保险柜中的金币
		SafeGold safeGold = dbDao.getBySql(
				"select sg.id,sg.gold,sg.user_id,sg.`password` from safe_gold sg where sg.user_id=?", SafeGold.class,
				user.getId());
		// 3.判断用户是否在保险柜中存入金币
		if (safeGold == null) {
			return MsgFactory.failMsg("您保险柜中没有存入金币，无法取出！");
		}
		// 4.判断用户保险柜中是否有这么多金币
		if (safeGold.getGold() < gold) {
			return MsgFactory.failMsg("您取出的金额大于保险柜中的金额，无法取出！");
		}
		// 定义标记
		Integer mark = 0;
		Integer userMark = 0;
		// 5.将金币取出，保险柜中金币减少，账户金币增加
		mark = dbDao.update("update safe_gold sg set sg.gold=sg.gold-? where sg.user_id=? and sg.gold>=0",
				SafeGold.class, gold, user.getId());
		userMark = dbDao.update("update user u set u.gold=u.gold+? where u.id=? and u.gold+?>=0 ", User.class, gold, user.getId(),gold);

		if (mark < 1 || userMark < 1) {
			throw new BusinessException("业务异常");
		}
		RedisCache.remove(CacheContants.user+userId);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<Integer> userSafeGet(String id) {
		if(StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		SafeGold safe = dbDao.getByExpression("EQ_userId", id, SafeGold.class);
		if(safe==null) {
			// 6.用户未在保险柜中存入过金币，则新建一条记录
			safe = new SafeGold();
			safe.setGold(0);
			safe.setUserId(id);
			safe.setPassword("88888888");
			dbDao.save(safe);
		}
		return MsgFactory.success(safe.getGold());
	}

}
