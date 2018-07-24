package com.huidao.service.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.entity.GameFire;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.game.IGameFireService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;

@Service
@Component
public class GameFireService implements IGameFireService {

	@Autowired
	private DBDao dbDao;

	@PostConstruct
	@Transactional
	public void init() {
		List<GameFire> list = new ArrayList<>();
		list.add(new GameFire(1, 0.28));
		list.add(new GameFire(2, 0.33));
		list.add(new GameFire(3, 0.37));
		list.add(new GameFire(4, 0.44));
		list.add(new GameFire(5, 0.49));
		list.add(new GameFire(6, 0.54));
		list.add(new GameFire(7, 0.59));
		list.add(new GameFire(8, 0.64));
		list.add(new GameFire(9, 0.7));
		for (GameFire gameFire : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("EQ_level", gameFire.getLevel());
			GameFire gameFire2 = dbDao.getByMap(map, GameFire.class);
			if (gameFire2 == null) {
				dbDao.save(gameFire);
			}
		}
	}

	@Override
	public MsgDto<List<GameFire>> findAll() {
		String str = RedisCache.get(CacheContants.fishing_fire_list);
		if (StringUtils.isNotBlank(str)) {
			return MsgFactory.success(JSONObject.parseArray(str, GameFire.class));
		}
		List<GameFire> list = dbDao.findByExpression("ORDER_level", "asc", GameFire.class);
		RedisCache.set(CacheContants.fishing_fire_list, list);
		return MsgFactory.success(list);
	}

	@Override
	public MsgDto<String> refreshCache() {
		RedisCache.remove(CacheContants.fishing_fire_list);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<Integer> updateGameFire(GameFire gameFire) {
		if (gameFire == null) {
			throw ParamException.param_not_exception;
		}

		if (gameFire.getLevel() == null) {
			throw ParamException.param_not_exception;
		}

		if (gameFire.getPower() == null) {
			throw ParamException.param_not_exception;
		}

		return MsgFactory.success(dbDao.update(gameFire));
	}

}
