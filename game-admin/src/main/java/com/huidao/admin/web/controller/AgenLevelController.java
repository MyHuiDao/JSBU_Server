package com.huidao.admin.web.controller;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.admin.web.manager.SysUserManager;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.AgenLevel;
import com.huidao.common.entity.SysUser;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IAgenLevelService;
import com.huidao.common.interfaces.admin.ISysUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;

@Controller
@RequestMapping("/agenLevel")
public class AgenLevelController {

	@Reference
	private IAgenLevelService agenLevelService;

	@Reference
	private ISysUserService sysUserService;

	@RequestMapping("/findAgenLevelAll")
	/* @Permission("agen_level_get") */
	@JSON
	public MsgDto<List<AgenLevel>> findAgenLevelAll() {

		return MsgFactory.success(agenLevelService.findAgenLevelAll());
	}

	@RequestMapping("/addAgenLevel")
	@Permission("agen_level_add")
	@JSON
	public MsgDto<String> addAgenLevel(AgenLevel agenLevel) {
		SysUser sysUser = SysUserManager.getSysUser();
		Date date = new Date();
		agenLevel.setCreateDate(date);
		agenLevel.setUpdateDate(date);
		agenLevel.setSysUserId(sysUser.getId());
		return agenLevelService.add(agenLevel);
	}

	@RequestMapping("/updateAgenLevel")
	@Permission("agen_level_update")
	@JSON
	public MsgDto<String> updateAgenLevel(AgenLevel agenLevel) {
		SysUser sysUser = SysUserManager.getSysUser();
		Date date = new Date();
		agenLevel.setUpdateDate(date);
		agenLevel.setSysUserId(sysUser.getId());
		return agenLevelService.update(agenLevel);
	}

	@RequestMapping("/deleteAgenLevel")
	@Permission("agen_level_delete")
	@JSON
	public MsgDto<String> deleteAgenLevel(String id) {
		List<SysUser> list = sysUserService.getAgenLevels(id);
		if (list.size() > 0) {
			return MsgFactory.failMsg("系统还有相关代理无法删除，请联系管理员!");
		}
		return agenLevelService.delete(id);
	}

	@RequestMapping("/getAgenLevel")
	@Permission("agen_level_get")
	@JSON
	public MsgDto<AgenLevel> getAgenLevel(String id) {

		return MsgFactory.success(agenLevelService.get(id));
	}

	@RequestMapping("/getNextLevel")
	@Permission
	@JSON
	public MsgDto<AgenLevel> getNextLevel(Integer level) {
		if (level == null) {
			throw ParamException.param_not_exception;
		}
		return MsgFactory.success(agenLevelService.getNextLevel(level));
	}

}
