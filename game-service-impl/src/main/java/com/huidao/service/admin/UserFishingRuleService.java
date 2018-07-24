package com.huidao.service.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.UserFishingRule;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IUserFishingRuleService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

/**
 * 用户捕鱼规则关联业务层
 * 
 * @author lenovo
 *
 */
@Component
@Service
public class UserFishingRuleService implements IUserFishingRuleService {
	@Autowired
	private DBDao dbDao;

	@Override
	public MsgDto<List<UserFishingRule>> getUserFishingRuleData(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("userId", id);
		return MsgFactory.success(dbDao.findBySqlName("getUserFishingRuleData", queryXmlSql, UserFishingRule.class));
	}

	@Override
	@Transactional
	public MsgDto<String> add(UserFishingRule userFishingRule) {
		// 1.校验相关值是否为空
		if (userFishingRule == null) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(userFishingRule.getUserId())) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(userFishingRule.getFishingRuleId())) {
			throw ParamException.param_not_exception;
		}

		// 2.判断当前规则是否已添加
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("EQ_userId", userFishingRule.getUserId());
		maps.put("EQ_fishingRuleId", userFishingRule.getFishingRuleId());
		UserFishingRule userFishingRule2 = dbDao.getByMap(maps, UserFishingRule.class);
		if (userFishingRule2 != null) {
			return MsgFactory.failMsg("该规则已配置");
		}
		// 默认情况下规则添加就开启
		userFishingRule.setStatus(0);
		// 3.将捕鱼规则保存到关联表中
		dbDao.save(userFishingRule);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> update(String id, Integer status) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		if (status == null) {
			throw ParamException.param_not_exception;
		}
		UserFishingRule userFishingRule2 = dbDao.get(id, UserFishingRule.class);
		userFishingRule2.setStatus(status);
		dbDao.update(userFishingRule2);
		return MsgFactory.success();
	}

	@Override
	@Transactional
	public MsgDto<String> delete(String id) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		dbDao.deleteById(id, UserFishingRule.class);
		return MsgFactory.success();
	}

}
