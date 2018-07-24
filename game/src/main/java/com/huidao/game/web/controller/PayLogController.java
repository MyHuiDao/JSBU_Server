package com.huidao.game.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.huidao.common.anno.JSON;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.User;
import com.huidao.common.interfaces.game.IPayLogService;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.common.msg.MsgDto;
import com.huidao.common.util.HttpUtil;
import com.huidao.game.manager.UserManager;

@Controller
@RequestMapping(value = "/pay")
public class PayLogController {

	private static final Logger log = LoggerFactory.getLogger(PayLogController.class);
	@Reference
	private IPayLogService payLogService;

	@Reference
	private IUserService userService;

	@RequestMapping(value = "/order")
	@JSON
	@Permission
	public MsgDto<Map<String, String>> order(String goldId, HttpServletRequest request, String productcode,
			String terminal, Integer type) {
		String ip = HttpUtil.getIp(request);
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		return payLogService.order(goldId, ip, user.getId(), user.getCode() + "", productcode, terminal, type);
	}

	@RequestMapping(value = "/applePayOrder")
	@JSON
	@Permission
	public MsgDto<String> applePayOrder(String goldId, HttpServletRequest request) {
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		return payLogService.applePayOrder(goldId, user.getId());
	}

	@RequestMapping(value = "/callback")
	@ResponseBody
	public String callback(HttpServletRequest request) {
		String p1_yingyongnum = request.getParameter("p1_yingyongnum");
		String p2_ordernumber = request.getParameter("p2_ordernumber");
		String p3_money = request.getParameter("p3_money");
		String p4_zfstate = request.getParameter("p4_zfstate");
		String p5_orderid = request.getParameter("p5_orderid");
		String p6_productcode = request.getParameter("p6_productcode");
		String p7_bank_card_code = request.getParameter("p7_bank_card_code");
		String p8_charset = request.getParameter("p8_charset");
		String p9_signtype = request.getParameter("p9_signtype");
		String p10_sign = request.getParameter("p10_sign");
		String p11_pdesc = request.getParameter("p11_pdesc");
		p7_bank_card_code = p7_bank_card_code == null ? "" : p7_bank_card_code;
		log.info("回调url参数:" + request.getQueryString() + "回调参数：" + JSONObject.toJSONString(request.getParameterMap()));
		try {
			return payLogService.callback(p1_yingyongnum, p2_ordernumber, p3_money, p4_zfstate, p5_orderid,
					p6_productcode, p7_bank_card_code, p8_charset, p9_signtype, p10_sign, p11_pdesc).getData();
		} catch (Exception e) {
			return "fail";
		}
	}

	@RequestMapping(value = "/applePaycallback")
	@ResponseBody
	@Permission
	public MsgDto<Integer> applePaycallback(String orderId, String voucher, Integer type) {
		User user = userService.getUserByToken(UserManager.getCurrentToken());
		return payLogService.applePaycallback(user.getId(), orderId, voucher, type);
	}

}
