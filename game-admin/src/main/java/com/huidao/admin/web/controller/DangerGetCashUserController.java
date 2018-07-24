package com.huidao.admin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.admin.web.manager.SysUserManager;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.DangerGetCashUser;
import com.huidao.common.entity.SysUser;
import com.huidao.common.interfaces.admin.IDangerGetCashUserService;
import com.huidao.common.msg.MsgDto;
import com.yehebl.orm.data.common.dto.Page;
import com.yehebl.orm.data.common.dto.PageUtil;

@Controller
@RequestMapping("/dgcu")
public class DangerGetCashUserController {

	@Reference
	private IDangerGetCashUserService dangerGetCashUserService;

	@RequestMapping("/getDangerGetCashUserAll")
	@Permission("high_risk_user_disabled")
	@JSON
	public MsgDto<Page<DangerGetCashUser>> getDangerGetCashUserAll(String code) {

		return dangerGetCashUserService.getDangerGetCashUserAll(PageUtil.getPage().getPage(),
				PageUtil.getPage().getSize(), code);
	}

	@RequestMapping("/getDangerGetCashUserSelf")
	@Permission("high_risk_user_disabled")
	@JSON
	public MsgDto<Page<DangerGetCashUser>> getDangerGetCashUserSelf(String userId) {

		return dangerGetCashUserService.getDangerGetCashUserSelf(PageUtil.getPage().getPage(),
				PageUtil.getPage().getSize(), userId);
	}

	@RequestMapping("/release")
	@Permission("high_risk_user_release")
	@JSON
	public MsgDto<Integer> release(String id) {
		SysUser sysUser = SysUserManager.getSysUser();
		return dangerGetCashUserService.release(id, sysUser.getId());
	}

}
