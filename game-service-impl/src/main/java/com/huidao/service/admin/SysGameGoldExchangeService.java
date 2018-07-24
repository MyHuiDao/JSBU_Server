package com.huidao.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.entity.GameGoldExchange;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.ISysGameGoldExchangeService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

/**
 * 人民币兑换金币设置
 * 
 * @author lenovo
 *
 */
@Service
@Component
public class SysGameGoldExchangeService implements ISysGameGoldExchangeService {
	@Autowired
	private DBDao dbDao;

	/**
	 * 分页显示兑换比例
	 */
	@Override
	public MsgDto<Page<GameGoldExchange>> findGameGoldExchangeAll(Integer page, Integer size, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ORDER_seq", "asc");
		if (type != null) {
			map.put("EQ_type", type);
		}
		return MsgFactory.success(dbDao.findPageByMap(page, size, map, GameGoldExchange.class));
	}

	/**
	 * 添加比例
	 */
	@Override
	@Transactional
	public MsgDto<String> add(GameGoldExchange gameGoldExchange) {
		if (gameGoldExchange == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameGoldExchange.getGold().toString())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameGoldExchange.getRmb().toString())) {
			throw ParamException.param_not_exception;
		}
		if (gameGoldExchange.getType() == null) {
			throw ParamException.param_not_exception;
		}
		dbDao.save(gameGoldExchange);
		return MsgFactory.success();
	}

	/**
	 * 删除比例
	 */
	@Override
	@Transactional
	public MsgDto<String> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		dbDao.deleteById(id, GameGoldExchange.class);
		return MsgFactory.success();
	}

	/**
	 * 修改比例
	 */
	@Override
	@Transactional
	public MsgDto<String> update(GameGoldExchange gameGoldExchange) {
		if (gameGoldExchange == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameGoldExchange.getGold().toString())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameGoldExchange.getRmb().toString())) {
			throw ParamException.param_not_exception;
		}
		dbDao.update(gameGoldExchange);
		return MsgFactory.success();
	}

	/**
	 * 通过id获取比例信息
	 */
	@Override
	public MsgDto<GameGoldExchange> getGameGoldExchange(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.get(id, GameGoldExchange.class));
	}

	@Override
	public MsgDto<String> refresh() {
		RedisCache.remove(CacheContants.gold_exchange + 0);
		RedisCache.remove(CacheContants.gold_exchange + 1);
		return MsgFactory.successMsg("刷新缓存成功");
	}

	@Override
	public MsgDto<List<GameGoldExchange>> getGameGoldExchangeType(Integer type) {
		if (type == null) {
			throw ParamException.param_not_exception;
		}

		QueryXmlSql queryXmlSql = new QueryXmlSql();
		return MsgFactory
				.success(dbDao.findBySqlName("findProxyGameGoldExchange", queryXmlSql, GameGoldExchange.class));
	}

}
