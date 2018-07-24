package com.huidao.service.game;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.entity.GameSetting;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;

@Service
@Component
public class GameSettingService implements IGameSettingService {

	@Autowired
	private DBDao dbDao;

	@PostConstruct
	public void init() {
		initKeyValue(GameSettingContants.recommend_url, "http://jinshayugang.com/game/user/recommendWeixin", "推广链接");
		/*
		 * initKeyValue(GameSettingContants.fish_probability, "500", "鱼概率系数");
		 * initKeyValue(GameSettingContants.fish_room_min_count, "70", "鱼最少数量");
		 * initKeyValue(GameSettingContants.fish_room_max_count, "100", "鱼最大数量");
		 */
		initKeyValue(GameSettingContants.safa_save_gold, "100000", "保险柜存取倍数");
		initKeyValue(GameSettingContants.gold_exchange_rate, "1000", "人民币兑换金币汇率");
		initKeyValue(GameSettingContants.init_experience_num, "10000", "初始化体验币");
		initKeyValue(GameSettingContants.game_get_cash_add, "100", "需要赢的金币大于多少才能提现(RMB)");
		initKeyValue(GameSettingContants.game_onec_get_cash, "1", "游戏首次提现大于多少(RMB)");
		initKeyValue(GameSettingContants.game_onec_pay, "10", "首次充值大于多少(RMB)");
		initKeyValue(GameSettingContants.game_left_money, "10", "游戏最少剩余多少(RMB)");
		initKeyValue(GameSettingContants.fishing_game_rule, "http://jinshayugang.com/ruleImg/fishing_rule.png",
				"捕鱼游戏规则图片");
		initKeyValue(GameSettingContants.game_agent_get_case, "100", "代理提现需要多少的整数倍");
		initKeyValue(GameSettingContants.game_min_pay, "19.99", "最低充值不能低於多少");
		initKeyValue(GameSettingContants.customer_weixin, "小小:123456,大大:123456", "微信客服逗号分隔");
		initKeyValue(GameSettingContants.min_fish_group, (5 * 60) + "", "多少秒触发一次鱼阵");
		initKeyValue(GameSettingContants.launch_frozen_skills, 180 + "", "发动技能减少金币");
		initKeyValue(GameSettingContants.app_url, "http://jinshayugang.com/download.html", "下载地址");
		initKeyValue(GameSettingContants.app_version, "1.0.1", "安卓版本号");
		initKeyValue(GameSettingContants.app_ios_url, "http://jinshayugang.com/download.html", "下载地址");
		initKeyValue(GameSettingContants.app_ios_version, "1.0.0", "苹果版本号");
		initKeyValue(GameSettingContants.user_consume_gold, "50", "用户消耗提现比例");
		initKeyValue(GameSettingContants.user_danger_consume_gold, "70", "高危用户消耗提现比例");
		initKeyValue(GameSettingContants.user_danger_consume_rule, "0-50-60-3,1-50-60-4,2-50-60-5",
				"高危用户规则判断,号分隔，小时-比例起-比例止-次数");
		initKeyValue(GameSettingContants.match_fish_odds_number1, "1.75", "单个中奖赔率");
		initKeyValue(GameSettingContants.match_fish_odds_number2, "7.55", "组合中奖赔率");
		initKeyValue(GameSettingContants.match_fish_seconds, "30", "开奖倒计时秒数");
		initKeyValue(GameSettingContants.match_fish_room_people_number, "20", "赛鱼房间人数");
		initKeyValue(GameSettingContants.whether_open_proxy_features, "Y", "是否开启代理功能 Y开启 N关闭");
		initKeyValue(GameSettingContants.register_agent_free_gold, "5000", "注册代理赠送免费金币");
		initKeyValue(GameSettingContants.game_switch, "N", "兑换开关");
		initKeyValue(GameSettingContants.switch_game, "Y", "游戏开关");
		initKeyValue(GameSettingContants.fish_array_number, "2", "鱼阵数量");
	}

	@Transactional
	public void initKeyValue(String key, String value, String desc) {
		GameSetting gs = dbDao.getByExpression("EQ_key", key, GameSetting.class);
		if (gs == null) {
			gs = new GameSetting();
			gs.setKey(key);
			gs.setValue(value);
			gs.setDesc(desc);
			dbDao.save(gs);
		}
	}

	@Override
	public String getValue(String key) {
		if (StringUtils.isBlank(key)) {
			throw ParamException.param_not_exception;
		}
		String value = RedisCache.get(CacheContants.game_setting + key);
		if (StringUtils.isBlank(value)) {
			GameSetting gs = dbDao.getByExpression("EQ_key", key, GameSetting.class);
			if (gs == null) {
				throw new RuntimeException("获取不到" + key + "的值");
			}
			RedisCache.set(CacheContants.game_setting + key, gs.getValue());
			return gs.getValue();
		}
		return value;
	}

	@Override
	public void refresh(String key) {
		RedisCache.remove(CacheContants.game_setting + key);
	}

	@Override
	public MsgDto<Page<GameSetting>> findPage(Integer page, Integer size, String key) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isNotBlank(key)) {
			map.put("LIKE_key", key);
		}
		return MsgFactory.success(dbDao.findPageByMap(page, size, map, GameSetting.class));
	}

	/**
	 * 获取单个游戏设置对象
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public MsgDto<GameSetting> getSettingById(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.get(id, GameSetting.class));
	}

	/**
	 * 游戏设置更新
	 * 
	 * @param gameSetting
	 * @return
	 */
	@Override
	@Transactional
	public MsgDto<String> update(String sysUserAccount, GameSetting gameSetting) {
		if (StringUtils.isBlank(sysUserAccount)) {
			throw ParamException.param_not_exception;
		}
		if (gameSetting == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameSetting.getValue())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameSetting.getId())) {
			throw ParamException.param_not_exception;
		}
		GameSetting gs = dbDao.get(gameSetting.getId(), GameSetting.class);
		if (gs == null) {
			throw ParamException.param_exception;
		}
		gs.setValue(gameSetting.getValue());
		gs.setUpdateTime(new Date());
		gs.setAdminUserId(sysUserAccount);// 获取帐号名
		dbDao.update(gs);
		refresh(gs.getKey());
		return MsgFactory.successMsg("修改成功");
	}

	/**
	 * 通过key获取gamesetting
	 */
	@Override
	public GameSetting getKeyGameSetting(String key) {
		if (StringUtils.isBlank(key)) {
			throw ParamException.param_not_exception;
		}
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("EQ_key", key);
		return dbDao.getByMap(maps, GameSetting.class);
	}

}
