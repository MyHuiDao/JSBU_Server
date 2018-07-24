package com.huidao.admin.web.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.JSON;
import com.huidao.common.entity.AgenApply;
import com.huidao.common.entity.User;
import com.huidao.common.exception.ParamException;
import com.huidao.common.interfaces.admin.IAgenApplyService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.msg.MsgFactory;
import com.huidao.common.util.CodeUtil;

@Controller
@RequestMapping("/agenApply")
public class AgenApplyController {

	@Reference
	private IAgenApplyService agenApplyService;
	@Reference
	private IUserService userService;

	/**
	 * 代理商申请
	 * 
	 * @param id
	 *            用户游戏id
	 * @param account
	 *            账号
	 * @param password
	 *            密码
	 * @return
	 */
	@RequestMapping("/addAgenApply")
	@JSON
	public MsgDto<String> addAgenApply(String token, String account, String password) {
		if (StringUtils.isBlank(token)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(account)) {
			throw ParamException.param_not_exception;
		}
		if (StringUtils.isBlank(password)) {
			throw ParamException.param_not_exception;
		}
		// 判断账号是否已存在
		AgenApply isAgenApply = agenApplyService.getAgenApply(account);
		if (isAgenApply != null) {
			return MsgFactory.failMsg("账号已存在，请重新输入");
		}
		// 1.通过用户token获取用户信息
		User user = userService.getUserByToken(token);
		if (StringUtils.isBlank(user.getMobile())) {
			return MsgFactory.failMsg("请绑定手机号码后申请");
		}
		// 判断用户是否被禁用
		if (user.getUserState().equals("1")) {
			return MsgFactory.failMsg("您的账号已被禁用，请联系客户");
		}
		AgenApply agenApply = new AgenApply();
		// 2.设置用户游戏id
		agenApply.setGameUserId(user.getId());
		// 3.设置用户后台账号
		agenApply.setAccount(account);
		// 4.设置用户后台密码
		agenApply.setPassword(CodeUtil.md5Encode(user.getSalt() + password));
		// 5.设置用户的盐
		agenApply.setSalt(user.getSalt());
		// 6.设置申请时间
		agenApply.setApplyDate(new Date());
		// 7.设置审核状态为未审核状态
		agenApply.setAuditStatus(0);
		agenApplyService.add(agenApply);
		return MsgFactory.success();
	}

}
