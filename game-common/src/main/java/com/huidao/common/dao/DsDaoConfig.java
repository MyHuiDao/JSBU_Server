package com.huidao.common.dao;

import com.yehebl.orm.config.AbstractDsConfig;

public class DsDaoConfig extends AbstractDsConfig {

	@Override
	public String getScanPackage() {
		return "com.huidao.common.entity.*,com.huidao.common.dto";
	}
	
	
	@Override
	public String getScanXml() {
		return "/queryXml/query-.*\\.xml";
	}

}
