package com.huidao.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.GameUserOnce;
import com.huidao.common.enums.GameUserOnceType;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.game.IGameUserOnceService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;

@Service
@Component
public class GameUserOnceService implements IGameUserOnceService {

	@Autowired
	private DBDao dbDao;

	@Override
	@Transactional
	public MsgDto<String> addGameUserOnce(String userId, GameUserOnceType onceType) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		if (onceType == null) {
			throw ParamException.param_not_exception;
		}
		GameUserOnce guo = new GameUserOnce();
		guo.setType(onceType.getType());
		guo.setUserId(userId);
		dbDao.save(guo);
		return MsgFactory.success();
	}

	@Override
	public MsgDto<Boolean> isGameUserOnce(String userId, GameUserOnceType onceType) {
		if (StringUtils.isBlank(userId)) {
			throw ParamException.param_not_exception;
		}
		if (onceType == null) {
			throw ParamException.param_not_exception;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("EQ_userId", userId);
		map.put("EQ_type", onceType.getType());
		Integer count = dbDao.getCountByMap(map, GameUserOnce.class);
		if (count > 0) {
			return MsgFactory.success(false);
		}
		return MsgFactory.success(true);
	}

}
