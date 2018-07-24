package com.huidao.service.admin;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.entity.FishOdds;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IFishOddsService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;

/**
 * 捕鱼赔率业务层
 * 
 * @author lenovo
 *
 */
@Component
@Service
public class FishOddsService implements IFishOddsService {

	@Autowired
	private DBDao dbDao;

	/**
	 * 查询所有捕鱼赔率
	 */
	@Override
	public List<FishOdds> getFishOddsAll() {
		String str = RedisCache.get(CacheContants.fish_odds_list);
		if(StringUtils.isNotBlank(str)) {
			return JSONObject.parseArray(str, FishOdds.class);
		}
		List<FishOdds> list = dbDao.findAll(FishOdds.class);
		RedisCache.set(CacheContants.fish_odds_list, list);
		return list;
	}

	/**
	 * 添加捕鱼赔率
	 */
	@Override
	@Transactional
	public MsgDto<String> add(FishOdds fishOdds) {
		if (fishOdds == null) {
			throw ParamException.param_not_exception;
		}
		if (fishOdds.getShouzhibiJs() == null) {
			throw ParamException.param_not_exception;
		}
		if (fishOdds.getShouzhibiKs() == null) {
			throw ParamException.param_not_exception;
		}
		if (fishOdds.getOdds() == null) {
			throw ParamException.param_not_exception;
		}
		dbDao.save(fishOdds);
		return MsgFactory.success();
	}

	/**
	 * 修改捕鱼赔率
	 */
	@Override
	@Transactional
	public MsgDto<String> update(FishOdds fishOdds) {
		if (fishOdds == null) {
			throw ParamException.param_not_exception;
		}
		if (fishOdds.getShouzhibiJs() == null) {
			throw ParamException.param_not_exception;
		}
		if (fishOdds.getShouzhibiKs() == null) {
			throw ParamException.param_not_exception;
		}
		if (fishOdds.getOdds() == null) {
			throw ParamException.param_not_exception;
		}
		dbDao.update(fishOdds);
		return MsgFactory.success();
	}

	/**
	 * 删除捕鱼赔率
	 */
	@Override
	@Transactional
	public MsgDto<String> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		dbDao.deleteById(id, FishOdds.class);
		return MsgFactory.success();
	}

	/**
	 * 获取捕鱼赔率
	 */
	@Override
	public FishOdds get(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}

		return dbDao.get(id, FishOdds.class);
	}
	
}
