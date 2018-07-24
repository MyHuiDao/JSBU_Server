package com.huidao.service.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.AgenLevel;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IAgenLevelService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;

@Component
@Service
public class AgenbLevelService implements IAgenLevelService {

	@Transactional
	@PostConstruct
	public void init() {
		Date d = new Date();
		List<AgenLevel> list = new ArrayList<>();
		list.add(new AgenLevel(BigDecimal.ZERO, 20, 0, 0, 1, d, d, "1", "免费代理"));
		list.add(new AgenLevel(new BigDecimal(200), 30, 1, 0, 2, d, d, "2", "黄金代理"));
		list.add(new AgenLevel(new BigDecimal(888), 40, 8, 3, 3, d, d, "3", "白金代理"));
		list.add(new AgenLevel(new BigDecimal(2888), 50, 10, 5, 4, d, d, "4", "钻石代理"));
		for (AgenLevel al : list) {
			if (dbDao.getByExpression("EQ_level", al.getLevel(), AgenLevel.class) == null) {
				dbDao.save(al);
			}
		}

	}

	@Autowired
	private DBDao dbDao;

	/**
	 * 获取所有代理级别
	 */
	@Override
	public List<AgenLevel> findAgenLevelAll() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ORDER_level", "asc");
		return dbDao.findByMap(map, AgenLevel.class);
	}

	@Override
	@Transactional
	public MsgDto<String> add(AgenLevel agenLevel) {
		if (agenLevel == null) {
			throw ParamException.param_not_exception;
		}
		if (agenLevel.getLevel() == null) {
			throw ParamException.param_not_exception;
		}
		if (agenLevel.getProportion1() == null) {
			throw ParamException.param_not_exception;
		}
		if (agenLevel.getProportion2() == null) {
			throw ParamException.param_not_exception;
		}
		if (agenLevel.getProportion3() == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(agenLevel.getName())) {
			throw ParamException.param_not_exception;
		}
		dbDao.save(agenLevel);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> update(AgenLevel agenLevel) {
		if (agenLevel == null) {
			throw ParamException.param_not_exception;
		}
		if (agenLevel.getLevel() == null) {
			throw ParamException.param_not_exception;
		}
		if (agenLevel.getProportion1() == null) {
			throw ParamException.param_not_exception;
		}
		if (agenLevel.getProportion2() == null) {
			throw ParamException.param_not_exception;
		}
		if (agenLevel.getProportion3() == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(agenLevel.getName())) {
			throw ParamException.param_not_exception;
		}
		agenLevel.setUpdateDate(new Date());
		dbDao.update(agenLevel);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		dbDao.deleteById(id, AgenLevel.class);
		return MsgFactory.success();
	}

	@Override
	public AgenLevel get(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		return dbDao.get(id, AgenLevel.class);
	}

	@Override
	public AgenLevel getNextLevel(Integer level) {
		if (level == null) {
			throw ParamException.param_not_exception;
		}
		return dbDao.createExpressionMap().gt("level", level).order("level").limit(1).get(AgenLevel.class);
	}

}
