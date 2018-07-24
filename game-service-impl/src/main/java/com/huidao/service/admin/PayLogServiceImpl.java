package com.huidao.service.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.PayLog;
import com.huidao.common.interfaces.admin.ISysPayLogService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

@Component
@Service
public class PayLogServiceImpl implements ISysPayLogService {

	@Autowired
	private DBDao dbDao;

	@Override
	public MsgDto<Page<PayLog>> findPayLogAll(Integer page, Integer size, String code, String status) {
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		if (StringUtils.isNotBlank(code)) {
			queryXmlSql.addParams("code", code);
		}
		if (StringUtils.isNotBlank(status)) {
			queryXmlSql.addParams("status", status);
		}
		return MsgFactory.success(dbDao.findPageBySqlName("findPayLogAll", queryXmlSql, page, size, PayLog.class));
	}
	
	
	
}
