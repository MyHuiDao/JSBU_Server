package com.huidao.service.admin;

import com.alibaba.dubbo.config.annotation.Service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huidao.common.entity.SysUserGetCash;
import com.huidao.common.interfaces.admin.ISysUserGetCashService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

@Component
@Service
public class SysUserGetCashService implements ISysUserGetCashService {
	@Autowired
	private DBDao dbDao;

	@Override
	public MsgDto<Page<SysUserGetCash>> findSysUserGetCash(Integer page, Integer size, String code) {
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		if (StringUtils.isNotBlank(code)) {
			queryXmlSql.addParams("code", code);
		}
		return MsgFactory
				.success(dbDao.findPageBySqlName("findSysUserGetCash", queryXmlSql, page, size, SysUserGetCash.class));
	}

}
