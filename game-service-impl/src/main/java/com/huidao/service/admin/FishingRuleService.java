package com.huidao.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.entity.FishingRule;
import com.huidao.common.entity.UserFishingRule;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IFishingRuleService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

/**
 * 捕鱼规则业务类
 * 
 * @author lenovo
 *
 */
@Component
@Service
public class FishingRuleService implements IFishingRuleService {
	@Autowired
	private DBDao dbDao;

	/**
	 * 分页显示捕鱼规则
	 */
	@Override
	public List<FishingRule> findPageFishingRule(Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (type != null) {
			map.put("EQ_type", type);
		}
		return dbDao.findByMap(map, FishingRule.class);
	}

	/**
	 * 添加游戏规则
	 */
	@Override
	@Transactional
	public MsgDto<String> add(FishingRule fishingRule) {
		if (fishingRule == null) {
			throw ParamException.param_not_exception;
		}
		if (fishingRule.getMaxGold()==null) {
			throw ParamException.param_not_exception;
		}
		if (fishingRule.getMinGold()==null) {
			throw ParamException.param_not_exception;
		}
		if (fishingRule.getWinNum()==null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(fishingRule.getName())) {
			throw ParamException.param_not_exception;
		}
		if (fishingRule.getType()==null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(fishingRule.getRoomType())) {
			throw ParamException.param_not_exception;
		}
		dbDao.save(fishingRule);
		return MsgFactory.success();
	}

	/**
	 * 修改游戏规则
	 */
	@Override
	@Transactional
	public MsgDto<String> update(FishingRule fishingRule) {
		if (fishingRule == null) {
			throw ParamException.param_not_exception;
		}
		if (fishingRule.getMaxGold()==null) {
			throw ParamException.param_not_exception;
		}
		if (fishingRule.getMinGold()==null) {
			throw ParamException.param_not_exception;
		}
		if (fishingRule.getWinNum()==null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(fishingRule.getName())) {
			throw ParamException.param_not_exception;
		}
		if (fishingRule.getType()==null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(fishingRule.getRoomType())) {
			throw ParamException.param_not_exception;
		}
		dbDao.update(fishingRule);
		return MsgFactory.success();
	}

	/**
	 * 删除游戏规则
	 */
	@Override
	@Transactional
	public MsgDto<String> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		dbDao.deleteById(id, FishingRule.class);
		return MsgFactory.success();
	}

	/**
	 * 获取游戏规则
	 */
	@Override
	public MsgDto<FishingRule> getFishingRule(String id) {

		return MsgFactory.success(dbDao.get(id, FishingRule.class));
	}

	/**
	 * 获取所有公共规则
	 */
	@Override
	public MsgDto<List<FishingRule>> findPublicFishingRuleAll(String roomType) {
		String str = RedisCache.get(CacheContants.fishing_rule_public_list+roomType);
		if (StringUtils.isNotBlank(str)) {
			return MsgFactory.success(JSONObject.parseArray(str, FishingRule.class));
		}
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("EQ_type", 0);
		maps.put("EQ_roomType",roomType);
		List<FishingRule> list = dbDao.findByMap(maps, FishingRule.class);
		RedisCache.set(CacheContants.fishing_rule_public_list+roomType, list);
		return MsgFactory.success(list);
	}
	
	/**
	 * 获取房间规则
	 */
	@Override
	public MsgDto<List<FishingRule>> findRoomFishingRuleAll(String areaId,String roomType) {
		String str = RedisCache.get(CacheContants.fishing_rule_room_list+areaId+roomType);
		if (StringUtils.isNotBlank(str)) {
			return MsgFactory.success(JSONObject.parseArray(str, FishingRule.class));
		}
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("areaId", areaId);
		queryXmlSql.addParams("roomType", roomType);
		List<FishingRule> list = dbDao.findBySqlName("findPrivateFishingRuleByAreaId", queryXmlSql, FishingRule.class);
		RedisCache.set(CacheContants.fishing_rule_room_list+areaId+roomType, list);
		return MsgFactory.success(list);
	}

	/**
	 * 获取用户私人规则
	 */
	@Override
	public MsgDto<List<FishingRule>> findPrivateFishingRuleByUserId(String userId,String roomType) {
		String str = RedisCache.get(CacheContants.fishing_rule_private_list + userId+roomType);
		if (StringUtils.isNotBlank(str)) {
			return MsgFactory.success(JSONObject.parseArray(str, FishingRule.class));
		}
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("userId", userId);
		List<FishingRule> list = dbDao.findBySqlName("findPrivateFishingRuleByUserId", queryXmlSql, FishingRule.class);
		RedisCache.set(CacheContants.fishing_rule_private_list+ userId+roomType, list);
		return MsgFactory.success(list);
	}

	@Override
	public List<UserFishingRule> getUserId() {
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("type", 1);
		List<UserFishingRule> list = dbDao.findBySqlName("getUserId", queryXmlSql, UserFishingRule.class);
		return list;
	}
}
