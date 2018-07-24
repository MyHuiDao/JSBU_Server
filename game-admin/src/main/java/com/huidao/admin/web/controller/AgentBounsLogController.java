package com.huidao.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.AgentBonusLog;
import com.huidao.common.interfaces.admin.ISysAgentBonusLogService;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

@Controller
@RequestMapping("/agentBounsLog")
public class AgentBounsLogController {

	@Reference
	private ISysAgentBonusLogService agentBonusLogService;

	@RequestMapping("/findAgentBounsLogAll")
	@Permission("agent_bouns_log_get")
	@JSON
	public MsgDto<Page<AgentBonusLog>> findAgentBounsLogAll(String code) {

		return agentBonusLogService.findAgentBonusLog(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(), code);
	}

}
