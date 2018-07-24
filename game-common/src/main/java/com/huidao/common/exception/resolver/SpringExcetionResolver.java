package com.huidao.common.exception.resolver;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.huidao.common.exception.BusinessException;
import com.huidao.common.exception.NoAuthExcepition;
import com.huidao.common.exception.NoLoginException;
import com.huidao.common.exception.ParamException;
import com.huidao.common.msg.MsgFactory;

/**
 * http返回消息
 * 
 * @author tzd
 *
 */
@Component
public class SpringExcetionResolver implements HandlerExceptionResolver {
	private static final Logger log = LoggerFactory.getLogger(SpringExcetionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		log.error(ex.getMessage(), ex);
		if (ex instanceof ParamException) {
			// response.setStatus(HttpStatus.BAD_REQUEST.value()); // 设置状态码
			print(response, JSONObject.toJSONString(MsgFactory.failMsg(ex.getMessage())));
		} else if (ex instanceof BusinessException) {
			print(response,
					JSONObject.toJSONString(MsgFactory.failMsg(((BusinessException) ex).getCode(), ex.getMessage())));
		} else if (ex instanceof NoAuthExcepition) {
			// response.setStatus(HttpStatus.UNAUTHORIZED.value()); // 设置状态码
			print(response, JSONObject.toJSONString(MsgFactory.failMsg(ex.getMessage())));
		}else if (ex instanceof NoLoginException) {
			print(response, JSONObject.toJSONString(MsgFactory.failMsg("-2", ex.getMessage())));
		} else {
			print(response, JSONObject.toJSONString(MsgFactory.failMsg(ex.getMessage())));
			// response.setStatus(HttpStatus.BAD_REQUEST.value()); // 设置状态码
		}
		return new ModelAndView();
	}

	private void print(HttpServletResponse response, String msg) {
		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

}
