package com.huidao.service.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.entity.FishControllerProperty;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IFishControllerPropertyService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

@Service
@Component
public class FishControllerPropertyService implements IFishControllerPropertyService {

	@Autowired
	private DBDao dbDao;

	@Override
	public MsgDto<Page<FishControllerProperty>> findFishControllerProperty(Integer page, Integer size, Integer type) {
		if (type == null) {
			type = 0;
		}
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("type", type);
		return MsgFactory.success(dbDao.findPageBySqlName("findFishControllerProperty", queryXmlSql, page, size,
				FishControllerProperty.class));
	}

	@Override
	public MsgDto<Integer> add(FishControllerProperty fishControllerProperty, String userId, String gameId) {
		if (fishControllerProperty == null) {
			throw ParamException.param_not_exception;
		}
		RedisCache.remove(CacheContants.fish_base_property_rule);
		if (StringUtils.isNotBlank(userId)) {
			RedisCache.remove(CacheContants.fish_controller_property_rule + userId + fishControllerProperty.getType());
			FishControllerProperty fishControllerProperty2 = dbDao.createExpressionMap().eq("joinId", userId)
					.eq("type", fishControllerProperty.getType()).get(FishControllerProperty.class);
			if (fishControllerProperty2 != null) {
				fishControllerProperty2.setPlayerDangWei(fishControllerProperty.getPlayerDangWei());
				return MsgFactory.success(dbDao.update(fishControllerProperty2));
			} else {
				fishControllerProperty.setFireLevel(1);
				fishControllerProperty.setFireSendNum(1);
				fishControllerProperty.setFishLevel(1);
				fishControllerProperty.setRoomMultiple(1000);
				fishControllerProperty.setJoinId(userId);
				fishControllerProperty.setWholeDangWei(1.0);
				fishControllerProperty.setFishDangWei(1.0);
				fishControllerProperty.setFuDuDangWei(1.0);
				fishControllerProperty.setType(2);
				return MsgFactory.success(dbDao.save(fishControllerProperty));
			}
		}

		if (StringUtils.isNotBlank(gameId)) {
			RedisCache.remove(CacheContants.fish_controller_property_rule + gameId + fishControllerProperty.getType());
			FishControllerProperty fishControllerProperty2 = dbDao.createExpressionMap().eq("joinId", gameId)
					.eq("type", fishControllerProperty.getType()).get(FishControllerProperty.class);
			if (fishControllerProperty2 != null) {
				fishControllerProperty2.setFuDuDangWei(fishControllerProperty.getFuDuDangWei());
				return MsgFactory.success(dbDao.update(fishControllerProperty2));
			} else {
				fishControllerProperty.setFireLevel(1);
				fishControllerProperty.setFireSendNum(1);
				fishControllerProperty.setFishLevel(1);
				fishControllerProperty.setRoomMultiple(1000);
				fishControllerProperty.setJoinId(gameId);
				fishControllerProperty.setWholeDangWei(1.0);
				fishControllerProperty.setFishDangWei(1.0);
				fishControllerProperty.setPlayerDangWei(1.0);
				fishControllerProperty.setType(1);
				return MsgFactory.success(dbDao.save(fishControllerProperty));
			}
		}

		fishControllerProperty.setFireLevel(1);
		fishControllerProperty.setFireSendNum(1);
		fishControllerProperty.setFishLevel(1);
		fishControllerProperty.setRoomMultiple(1000);
		fishControllerProperty.setJoinId("");
		RedisCache.remove(CacheContants.fish_controller_property_rule + "" + fishControllerProperty.getType());
		return MsgFactory.success(dbDao.save(fishControllerProperty));
	}

	@Override
	public MsgDto<Integer> update(String id, Double wholeDangWei, Double playerDangWei, Double fishDangWei,
			Double fuDuDangWei) {
		FishControllerProperty fishControllerProperty = dbDao.get(id, FishControllerProperty.class);
		if (fishControllerProperty == null) {
			throw ParamException.param_not_exception;
		}
		RedisCache.remove(CacheContants.fish_controller_property_rule + fishControllerProperty.getJoinId()
				+ fishControllerProperty.getType());
		if (wholeDangWei != null) {
			fishControllerProperty.setWholeDangWei(wholeDangWei);
		}
		if (playerDangWei != null) {
			fishControllerProperty.setPlayerDangWei(playerDangWei);
		}
		if (fishDangWei != null) {
			fishControllerProperty.setFishDangWei(fishDangWei);
		}
		if (fuDuDangWei != null) {
			fishControllerProperty.setFuDuDangWei(fuDuDangWei);
		}
		return MsgFactory.success(dbDao.update(fishControllerProperty));
	}

	@Override
	public MsgDto<Integer> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		FishControllerProperty fishControllerProperty = dbDao.get(id, FishControllerProperty.class);
		if (fishControllerProperty != null) {
			RedisCache.remove(CacheContants.fish_controller_property_rule + fishControllerProperty.getJoinId()
					+ fishControllerProperty.getType());
		}
		return MsgFactory.success(dbDao.deleteById(id, FishControllerProperty.class));
	}

	@Override
	public MsgDto<FishControllerProperty> getFishControllerProperty(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.get(id, FishControllerProperty.class));
	}

	@Override
	public MsgDto<FishControllerProperty> getUserFishControllerProperty(String userId, String gameId) {
		String val = "";
		if (StringUtils.isNotBlank(userId)) {
			val = userId;
		}

		if (StringUtils.isNotBlank(gameId)) {
			val = gameId;
		}
		FishControllerProperty fishControllerProperty = dbDao.getByExpression("EQ_joinId", val,
				FishControllerProperty.class);
		return MsgFactory.success(fishControllerProperty);
	}

	@Override
	public MsgDto<Integer> deleteUserGameFishControllerProperty(String userId, String gameId, Integer type) {
		String val = "";
		if (StringUtils.isNotBlank(userId)) {
			val = userId;
		}
		if (StringUtils.isNotBlank(gameId)) {
			val = gameId;
		}
		return MsgFactory.success(dbDao.deleteByExpression("EQ_joinId", val, FishControllerProperty.class));
	}

}
