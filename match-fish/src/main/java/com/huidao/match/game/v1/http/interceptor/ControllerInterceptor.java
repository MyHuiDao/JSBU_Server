package com.huidao.match.game.v1.http.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ControllerInterceptor implements HandlerInterceptor {

	private static final Logger log = LoggerFactory.getLogger(ControllerInterceptor.class);
	private long startTime = 0;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		startTime = System.currentTimeMillis();
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			log.debug("执行的方法：" + ((HandlerMethod) handler).getBean().getClass() + "."
					+ ((HandlerMethod) handler).getMethod().getName());
		}
		log.debug("执行一个请求需要：{}毫秒", (System.currentTimeMillis() - startTime));

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
