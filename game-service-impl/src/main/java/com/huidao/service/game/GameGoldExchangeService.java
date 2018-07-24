package com.huidao.service.game;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.contants.GameSettingContants;
import com.huidao.common.entity.GameGoldExchange;
import com.huidao.common.enums.GameUserOnceType;
import com.huidao.common.interfaces.game.IGameGoldExchangeService;
import com.huidao.common.interfaces.game.IGameSettingService;
import com.huidao.common.interfaces.game.IGameUserOnceService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;

@Service
@Component
public class GameGoldExchangeService implements IGameGoldExchangeService {

	@Autowired
	private DBDao dbDao;

	@Autowired
	private IGameUserOnceService gameUserOnceService;

	@Autowired
	private IGameSettingService gameSettingService;

	@Override
	public MsgDto<List<GameGoldExchange>> getExChangeGoldList(String userId, Integer type) {
		if (type == null) {
			type = 0;
		}
		String result = RedisCache.get(CacheContants.gold_exchange + type);
		if (StringUtils.isNotBlank(result)) {
			List<GameGoldExchange> list = JSONObject.parseArray(result, GameGoldExchange.class);
			if (!gameUserOnceService.isGameUserOnce(userId, GameUserOnceType.once_pay).getData()) {
				Double minPay = Double.valueOf(gameSettingService.getValue(GameSettingContants.game_min_pay));
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getRmb().doubleValue() < minPay) {
						list.remove(i);
						i--;
					}
				}
			}
			return MsgFactory.success(list);
		}
		List<GameGoldExchange> list = dbDao.createExpressionMap().eq("type", type).order("seq")
				.find(GameGoldExchange.class);
		RedisCache.set(CacheContants.gold_exchange, list);
		if (!gameUserOnceService.isGameUserOnce(userId, GameUserOnceType.once_pay).getData()) {
			Double minPay = Double.valueOf(gameSettingService.getValue(GameSettingContants.game_min_pay));
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getRmb().doubleValue() < minPay) {
					list.remove(i);
					i--;
				}
			}
		}

		return MsgFactory.success(list);
	}

}
