package com.huidao.common.interfaces.admin;

import com.huidao.common.entity.SysUserGetCash;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;

public interface ISysUserGetCashService {

	public MsgDto<Page<SysUserGetCash>> findSysUserGetCash(Integer page, Integer size, String code);

}
