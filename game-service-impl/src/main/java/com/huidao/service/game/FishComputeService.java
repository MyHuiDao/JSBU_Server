package com.huidao.service.game;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.entity.FishBaseProperty;
import com.huidao.common.entity.FishControllerProperty;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.game.IFishComputeService;
import com.yehebl.orm.data.common.IDao;

@Service
@Component
public class FishComputeService implements IFishComputeService {

	@Autowired
	private IDao dao;

	@PostConstruct
	@Transactional
	public void init() {
		FishBaseProperty fbp = dao.createExpressionMap().eq("type", 0).getOne(FishBaseProperty.class);
		if (fbp == null) {
			fbp = new FishBaseProperty();
			fbp.setControllerBodyBaseNum(0.1);
			fbp.setControllerCatchBaseNum(1D);
			fbp.setControllerFuDuBaseNum(0.55);
			fbp.setControllerPlayerBaseNum(1.8);
			fbp.setFireAttack(10D);
			fbp.setFireConsume(10D);
			fbp.setFishBaseHp(1D);
			fbp.setFishBaseHpNum(20D);
			fbp.setFishHpCoefficient(4.6);
			fbp.setGoldBaseNum(10D);
			fbp.setGoldCoefficient(5D);
			fbp.setType(0);
			fbp.setJoinId("");
			dao.save(fbp);
		}

		FishControllerProperty fcp = dao.createExpressionMap().eq("type", 0).getOne(FishControllerProperty.class);
		if (fcp == null) {
			fcp = new FishControllerProperty();
			fcp.setFireLevel(1);
			fcp.setFireSendNum(1);
			fcp.setFishDangWei(2D);
			fcp.setFishLevel(1);
			fcp.setFuDuDangWei(3D);
			fcp.setPlayerDangWei(3D);
			fcp.setRoomMultiple(1000);
			fcp.setWholeDangWei(3D);
			fcp.setType(0);
			fcp.setJoinId("");
			dao.save(fcp);
		}
	}

	@Override
	public FishBaseProperty getFishBaseProperty(String areaId, String userId) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(areaId)) {
			throw ParamException.param_not_exception;
		}
		FishBaseProperty fishBaseProperty = RedisCache.get(CacheContants.fish_base_property_rule,
				FishBaseProperty.class);
		if (fishBaseProperty != null) {
			return fishBaseProperty;
		}
		FishBaseProperty one = dao.createExpressionMap().eq("type", 0).getOne(FishBaseProperty.class);
		if (one != null) {
			RedisCache.set(CacheContants.fish_base_property_rule, one);
		}
		return one;
	}

	@Override
	public FishControllerProperty getFishControllerProperty(String areaId, String userId) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(areaId)) {
			throw ParamException.param_not_exception;
		}
		FishControllerProperty one2 = getRedisFishControllerProperty(userId, 2);

		FishControllerProperty one1 = getRedisFishControllerProperty(areaId, 1);

		FishControllerProperty one = getRedisFishControllerProperty("", 0);

		if (one1 != null) {
			one.setFuDuDangWei(one1.getFuDuDangWei());
		}
		if (one2 != null) {
			one.setPlayerDangWei(one2.getPlayerDangWei());
		}
		return one;
	}

	public FishControllerProperty getRedisFishControllerProperty(String id, Integer type) {
		String str = RedisCache.get(CacheContants.fish_controller_property_rule + id + type);
		if ("rule_null".equals(str)) {
			return null;
		}
		FishControllerProperty fishControllerProperty = JSONObject.parseObject(str, FishControllerProperty.class);
		if (fishControllerProperty != null) {
			return fishControllerProperty;
		}
		FishControllerProperty fishControllerProperty2 = dao.createExpressionMap().eq("type", type).eq("joinId", id)
				.getOne(FishControllerProperty.class);
		if (fishControllerProperty2 != null) {
			RedisCache.set(CacheContants.fish_controller_property_rule + id + type, fishControllerProperty2);
		} else {
			RedisCache.set(CacheContants.fish_controller_property_rule + id + type, "rule_null");
		}
		return fishControllerProperty2;
	}

}
