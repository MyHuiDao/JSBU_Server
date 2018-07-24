package com.huidao.common.interfaces.admin;

import com.huidao.common.entity.AgentBonusLog;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

public interface ISysAgentBonusLogService {

	public MsgDto<Page<AgentBonusLog>> findAgentBonusLog(Integer page, Integer size, String code);

}
