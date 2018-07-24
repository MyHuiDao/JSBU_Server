package com.huidao.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.AgenApply;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IAgenApplyService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;

@Component
@Service
public class AgenApplyService implements IAgenApplyService {

	@Autowired
	private DBDao dbDao;

	/**
	 * 获取未审核的代理申请
	 */
	@Override
	public List<AgenApply> findAgenApplyAll(Object auditStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_auditStatus", auditStatus);
		return dbDao.findByMap(map, AgenApply.class);
	}

	/**
	 * 修改代理商信息
	 */
	@Override
	@Transactional
	public MsgDto<String> update(AgenApply agenApply) {
		if (agenApply == null) {
			throw ParamException.param_not_exception;
		}
		dbDao.update(agenApply);
		return MsgFactory.success();
	}

	/**
	 * 代理商申请
	 */
	@Override
	@Transactional
	public MsgDto<String> add(AgenApply agenApply) {
		if (agenApply == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(agenApply.getGameUserId())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(agenApply.getAccount())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(agenApply.getPassword())) {
			throw ParamException.param_not_exception;
		}
		dbDao.save(agenApply);
		return MsgFactory.success();
	}

	/**
	 * 判断用户是否存在
	 */
	@Override
	public AgenApply getAgenApply(String account) {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("EQ_account", account);
		return dbDao.getByMap(maps, AgenApply.class);
	}

	/**
	 * 获取所有代理用户信息
	 */
	@Override
	public List<AgenApply> getAgenApplyAll() {

		return dbDao.findAll(AgenApply.class);
	}

	@Override
	public AgenApply getAgenApplyType(String userId, Integer auditStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_gameUserId", userId);
		map.put("EQ_auditStatus", auditStatus);
		return dbDao.getByMap(map, AgenApply.class);
	}

}
