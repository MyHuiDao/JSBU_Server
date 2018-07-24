package com.huidao.admin.web.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huidao.common.util.VerificationCodeUtil;
import com.huidao.common.util.validate.IValidateCodeDB;

@Controller
@RequestMapping("/validate")
public class ValidateController {

	@Resource(name = "validateCodeDB")
	private IValidateCodeDB validateCodeDB;

	@RequestMapping("/imageCode")
	public void validateCode(HttpServletResponse response) throws IOException {
		VerificationCodeUtil.generateCodeAndPic(90, 20, response, validateCodeDB);
	}
}
