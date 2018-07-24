package com.huidao.game.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huidao.common.anno.Permission;
import com.huidao.common.entity.User;
import com.huidao.common.exception.NoLoginException;
import com.huidao.common.interfaces.game.IUserService;
import com.huidao.game.manager.CacheManager;
import com.huidao.game.manager.UserManager;

public class ControllerInterceptor implements HandlerInterceptor {
	
	@Reference
	private IUserService userService;

	private static final Logger log = LoggerFactory.getLogger(ControllerInterceptor.class);
	private long startTime = 0;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		startTime = System.currentTimeMillis();
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			Permission permision = ((HandlerMethod) handler).getMethodAnnotation(Permission.class);
			if(permision!=null) {
				User user = userService.getUserByToken(UserManager.getCurrentToken()); //获取当前登录用户
				if(user==null) {
					throw new NoLoginException();
				}
				CacheManager.resetTimeUser(user.getId());
				
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			log.info("执行的方法：" + ((HandlerMethod) handler).getBean().getClass() + "."
					+ ((HandlerMethod) handler).getMethod().getName());
		}
		log.info("执行一个请求需要：{}毫秒", (System.currentTimeMillis() - startTime));
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
