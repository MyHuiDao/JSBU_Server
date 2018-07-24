package com.huidao.service.admin;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.DangerGetCashUser;
import com.huidao.common.entity.SysUser;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IDangerGetCashUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

@Service
public class DangerGetCashUserService implements IDangerGetCashUserService {

	@Autowired
	private DBDao dbDao;

	@Override
	public MsgDto<Page<DangerGetCashUser>> getDangerGetCashUserAll(Integer page, Integer size, String code) {
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		if (StringUtils.isNotBlank(code)) {
			queryXmlSql.addParams("code", code);
		}
		return MsgFactory.success(
				dbDao.findPageBySqlName("getDangerGetCashUserAll", queryXmlSql, page, size, DangerGetCashUser.class));
	}

	@Override
	@Transactional
	public MsgDto<Integer> release(String id, String sysUserId) {
		if (StringUtils.isBlank(id)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(sysUserId)) {
			throw ParamException.param_not_exception;
		}
		// 1.获取高危用户记录
		DangerGetCashUser dangerGetCashUser = dbDao.get(id, DangerGetCashUser.class);
		// 2.设置为解除高危用户记录
		dangerGetCashUser.setStatus(1);
		dangerGetCashUser.setUpdateDate(new Date());
		dangerGetCashUser.setSysUserId(sysUserId);
		// 3.修改
		dbDao.update(dangerGetCashUser);
		// 4.修改用户提现记录表
		SysUser sysUser = dbDao.getByExpression("EQ_gameUserId", dangerGetCashUser.getUserId(), SysUser.class);
		String sql = "update sys_user_get_cash sugc set sugc.status='1' where sugc.sys_user_id=?";
		dbDao.update(sql, DangerGetCashUser.class, sysUser.getId());
		return MsgFactory.success();
	}

	@Override
	public MsgDto<Page<DangerGetCashUser>> getDangerGetCashUserSelf(Integer page, Integer size, String userId) {
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		queryXmlSql.addParams("userId", userId);
		return MsgFactory.success(
				dbDao.findPageBySqlName("getDangerGetCashUserSelf", queryXmlSql, page, size, DangerGetCashUser.class));
	}

}
