package com.huidao.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.GameArea;
import com.huidao.common.entity.GameAreaRule;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IGameAreaService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

@Component
@Service
public class SysGameAreaService implements IGameAreaService {
	@Autowired
	private DBDao dbDao;

	@Override
	public MsgDto<List<GameArea>> findGameAreaAll() {
		Map<String, Object> maps = new HashMap<String, Object>();
		return MsgFactory.success(dbDao.findByMap(maps, GameArea.class));
	}

	@Override
	@Transactional
	public MsgDto<Integer> add(GameArea gameArea) {
		if (gameArea == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameArea.getGameType())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameArea.getName())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameArea.getStatus())) {
			throw ParamException.param_not_exception;
		}
		if (gameArea.getSeq() == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameArea.getType())) {
			throw ParamException.param_not_exception;
		}
		if (gameArea.getMinNum() == null) {
			throw ParamException.param_not_exception;
		}
		if (gameArea.getRoomMax() == null) {
			throw ParamException.param_not_exception;
		}
		if (gameArea.getLevelGold() == null) {
			throw ParamException.param_not_exception;
		}
		if (gameArea.getMultiple() == null) {
			throw ParamException.param_not_exception;
		}
		if (gameArea.getFishMaxCount() == null) {
			throw ParamException.param_not_exception;
		}
		gameArea.setFishProbability(0);
		return MsgFactory.success(dbDao.save(gameArea));
	}

	@Override
	@Transactional
	public MsgDto<Integer> update(GameArea gameArea) {
		if (gameArea == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameArea.getGameType())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameArea.getName())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameArea.getStatus())) {
			throw ParamException.param_not_exception;
		}
		if (gameArea.getSeq() == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameArea.getType())) {
			throw ParamException.param_not_exception;
		}
		if (gameArea.getMinNum() == null) {
			throw ParamException.param_not_exception;
		}
		if (gameArea.getRoomMax() == null) {
			throw ParamException.param_not_exception;
		}
		if (gameArea.getLevelGold() == null) {
			throw ParamException.param_not_exception;
		}
		if (gameArea.getMultiple() == null) {
			throw ParamException.param_not_exception;
		}
		if (gameArea.getFishMaxCount() == null) {
			throw ParamException.param_not_exception;
		}
		gameArea.setFishProbability(0);
		return MsgFactory.success(dbDao.update(gameArea));
	}

	@Override
	@Transactional
	public MsgDto<Integer> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.deleteById(id, GameArea.class));
	}

	@Override
	public MsgDto<GameArea> get(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.get(id, GameArea.class));
	}

	@Override
	public MsgDto<List<GameArea>> getFishingRule(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("areaId", id);
		return MsgFactory.success(dbDao.findBySqlName("getFishingRule", queryXmlSql, GameArea.class));
	}

	@Override
	public MsgDto<Integer> addFishingRule(GameAreaRule gameAreaRule) {
		if (gameAreaRule == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(gameAreaRule.getAreaId())) {
			throw ParamException.param_not_exception;
		}

		if (StringUtils.isBlank(gameAreaRule.getRuleId())) {
			throw ParamException.param_not_exception;
		}
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("EQ_ruleId", gameAreaRule.getRuleId());
		parms.put("EQ_areaId", gameAreaRule.getAreaId());
		GameAreaRule gameAreaRule2 = dbDao.getByMap(parms, GameAreaRule.class);
		if (gameAreaRule2 != null) {
			return MsgFactory.failMsg("该规则已存在请添加其它规则!");
		}
		return MsgFactory.success(dbDao.save(gameAreaRule));
	}

	@Override
	public MsgDto<Integer> deleteGameAreaRule(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(dbDao.deleteById(id, GameAreaRule.class));
	}

}
