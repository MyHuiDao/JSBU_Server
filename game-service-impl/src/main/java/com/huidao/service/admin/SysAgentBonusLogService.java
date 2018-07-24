package com.huidao.service.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.huidao.common.entity.AgentBonusLog;
import com.huidao.common.interfaces.admin.ISysAgentBonusLogService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.yehebl.orm.DBDao;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.xml.QueryXmlSql;

@Component
@Service
public class SysAgentBonusLogService implements ISysAgentBonusLogService {

	@Autowired
	private DBDao dbDao;

	@Override
	public MsgDto<Page<AgentBonusLog>> findAgentBonusLog(Integer page, Integer size, String code) {
		QueryXmlSql queryXmlSql = new QueryXmlSql();
		if (StringUtils.isNotBlank(code)) {
			queryXmlSql.addParams("code", code);
		}
		return MsgFactory
				.success(dbDao.findPageBySqlName("findAgentBonusLog", queryXmlSql, page, size, AgentBonusLog.class));
	}

}
