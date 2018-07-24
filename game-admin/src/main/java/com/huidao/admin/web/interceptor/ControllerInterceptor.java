package com.huidao.admin.web.interceptor;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.huidao.admin.web.contants.SessionContants;
import com.huidao.admin.web.manager.SysUserManager;
import com.huidao.common.anno.Permission;
import com.huidao.common.anno.ValidateCode;
import com.huidao.common.entity.SysUser;
import com.huidao.common.exception.BusinessException;
import com.huidao.common.exception.NoAuthExcepition;
import com.huidao.common.exception.NoLoginException;
import com.huidao.common.spring.SpringServlet;
import com.huidao.common.util.validate.IValidateCodeDB;
import com.yehebl.orm.data.common.dto.PageUtil;

public class ControllerInterceptor implements HandlerInterceptor {

	@Resource(name = "validateCodeDB")
	private IValidateCodeDB validateCodeDB;

	private static final Logger log = LoggerFactory.getLogger(ControllerInterceptor.class);
	private long startTime = 0;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		startTime = System.currentTimeMillis();
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			String offline = (String) SpringServlet.getSession().getAttribute("offline");
			if (StringUtils.isNotBlank(offline)) {
				request.getSession().removeAttribute(SessionContants.USER_LOGIN_KEY);
				request.getSession().removeAttribute("offline");
				throw new BusinessException("您已被挤下线，请重新登录");
			}
			ValidateCode validateCode = ((HandlerMethod) handler).getMethodAnnotation(ValidateCode.class);
			if (validateCode != null) {
				String value = request.getHeader(validateCode.value());
				if (StringUtils.isBlank(value)) {
					throw new BusinessException("验证码未输入");
				}
				String string = validateCodeDB.get(validateCode.value());
				if (StringUtils.isBlank(string)) {
					throw new BusinessException("验证码未发送");
				}
				if (!value.toLowerCase().equals(string.toLowerCase())) {
					throw new BusinessException("验证码输入错误");
				}
				validateCodeDB.del(validateCode.value());
			}
			Permission permision = ((HandlerMethod) handler).getMethodAnnotation(Permission.class);
			if (permision != null) {
				SysUser sysUser = SysUserManager.getSysUser();
				if (sysUser == null) {
					throw NoLoginException.noLoginException;
				}
				if (!"1".equals(sysUser.getId())) {
					if (StringUtils.isNotBlank(permision.value())) {
						Set<String> set = SysUserManager.getPermission();
						String[] permissionValue = permision.value().split(",");
						boolean falg = false;
						for (int i = 0; i < permissionValue.length; i++) {
							if (set.contains(permissionValue[i])) {
								falg = true;
								break;
							}
						}
						if (!falg) {
							throw NoAuthExcepition.noAuthExcepition;
						}
					}
				}
			}
			String pageStr = request.getParameter("_page");
			if (StringUtils.isNotBlank(pageStr)) {
				Integer page = Integer.valueOf(pageStr);
				PageUtil.getPage().setPage(page);
			}
			String sizeStr = request.getParameter("_size");
			if (StringUtils.isNotBlank(sizeStr)) {
				Integer size = Integer.valueOf(sizeStr);
				PageUtil.getPage().setSize(size);
			}

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
