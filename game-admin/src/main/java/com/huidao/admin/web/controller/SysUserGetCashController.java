package com.huidao.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.SysUserGetCash;
import com.huidao.common.interfaces.admin.ISysUserGetCashService;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

@Controller
@RequestMapping("/sysUserGetCash")
public class SysUserGetCashController {
	@Reference
	private ISysUserGetCashService sysUserGetCashService;
	
	@RequestMapping("/findSysUserGetCash")
	@Permission("sysUserGetCash_get")
	@JSON
	public MsgDto<Page<SysUserGetCash>> findSysUserGetCash(String code) {

		return sysUserGetCashService.findSysUserGetCash(PageUtil.getPage().getPage(), PageUtil.getPage().getSize(),
				code);
	}

}
