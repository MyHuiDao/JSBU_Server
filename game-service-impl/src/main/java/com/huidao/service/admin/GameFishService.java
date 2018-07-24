package com.huidao.service.admin;

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
import com.huidao.common.entity.GameFish;
import com.huidao.common.enums.FishType;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IGameFishService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;

/**
 * 鱼种类业务层
 * 
 * @author lenovo
 *
 */
@Component
@Service
public class GameFishService implements IGameFishService {

	@Autowired
	private DBDao dbDao;

	/**
	 * 获取鱼种类所有数据
	 */
	@Override
	public List<GameFish> findGameFishData() {

		return dbDao.findAll(GameFish.class);
	}

	/**
	 * 添加鱼种类
	 */
	@Override
	@Transactional
	public MsgDto<String> add(GameFish gameFish) {

		if (gameFish == null) {
			throw ParamException.param_not_exception;
		}

		if (StringUtils.isBlank(gameFish.getName())) {
			throw ParamException.param_not_exception;
		}

		if (StringUtils.isBlank(gameFish.getType())) {
			throw ParamException.param_not_exception;
		}

		if (gameFish.getGoldMin() == null) {
			throw ParamException.param_not_exception;
		}
		if (gameFish.getGoldMax() == null) {
			throw ParamException.param_not_exception;
		}

		if (gameFish.getPower() == null) {
			throw ParamException.param_not_exception;
		}
		dbDao.save(gameFish);
		return MsgFactory.success();
	}

	/**
	 * 修改鱼种类
	 */
	@Override
	@Transactional
	public MsgDto<String> update(GameFish gameFish) {
		if (gameFish == null) {
			throw ParamException.param_not_exception;
		}

		if (StringUtils.isBlank(gameFish.getName())) {
			throw ParamException.param_not_exception;
		}

		if (StringUtils.isBlank(gameFish.getType())) {
			throw ParamException.param_not_exception;
		}

		if (gameFish.getGoldMin() == null) {
			throw ParamException.param_not_exception;
		}
		if (gameFish.getGoldMax() == null) {
			throw ParamException.param_not_exception;
		}

		if (gameFish.getPower() == null) {
			throw ParamException.param_not_exception;
		}
		dbDao.update(gameFish);
		return MsgFactory.success();
	}

	/**
	 * 删除鱼种类
	 */
	@Override
	@Transactional
	public MsgDto<String> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		dbDao.deleteById(id, GameFish.class);
		return MsgFactory.success();
	}

	/**
	 * 获取鱼种类
	 */
	@Override
	public GameFish getGameFish(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return dbDao.get(id, GameFish.class);
	}

	/**
	 * 获取鱼种类
	 */
	@Override
	public GameFish getNameGameFish(String name) {
		if (StringUtils.isBlank(name)) {
			throw ParamException.param_not_exception;
		}
		Map<String, Object> maps = new HashMap<>();
		maps.put("EQ_name", name);
		return dbDao.getByMap(maps, GameFish.class);
	}

	// 初始化
	@PostConstruct
	public void init() {
		Map<String, Object> maps = new HashMap<String, Object>();
		// 1.循环遍历鱼种类
		for (FishType fishType : FishType.values()) {
			// 2.判断数据库中是否有该鱼种
			maps.put("EQ_type", fishType.getType());
			GameFish gameFish = dbDao.getByMap(maps, GameFish.class);
			// 判断是否有该鱼种，若没有则初始化添加
			if (gameFish == null) {
				GameFish newGameFish = new GameFish();
				// 设置名称
				newGameFish.setName(fishType.getName());
				// 设置类型
				newGameFish.setType(fishType.getType());
				newGameFish.setSpeed(fishType.getSpeed());
				newGameFish.setContrast(fishType.getContrast());
				// 初始化操作
				newGameFish.setGoldMax(fishType.getGoldMax());
				newGameFish.setGoldMin(fishType.getGoldMin());
				newGameFish.setPower(fishType.getPower());
				dbDao.save(newGameFish);
			}
		}
	}

	@Override
	public MsgDto<List<GameFish>> getFishInfoList() {
		String fishInfoStr = RedisCache.get(CacheContants.fish_info_list);
		if (StringUtils.isNotBlank(fishInfoStr)) {
			return MsgFactory.success(JSONObject.parseArray(fishInfoStr, GameFish.class));
		}
		List<GameFish> list = dbDao.findAll(GameFish.class);
		RedisCache.set(CacheContants.fish_info_list, list);
		return MsgFactory.success(list);
	}

	@Override
	public GameFish getGameFishType(String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_type", type);
		return dbDao.getByMap(map, GameFish.class);
	}

}
