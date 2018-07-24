package com.huidao.admin.web.manager;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.huidao.admin.web.contants.SessionContants;
import com.huidao.common.entity.SysUser;
import com.huidao.common.spring.SpringRequest;

public class SysUserManager {
	/**
	 * 获取session中用户公共类
	 * 
	 * @param request
	 * @return
	 */
	public static SysUser getSysUser() {
		HttpSession session = SpringRequest.getRequest().getSession();
		SysUser sysUser = (SysUser) session.getAttribute(SessionContants.USER_LOGIN_KEY);
		return sysUser;
	}
	
	/**
	 * 获取session中用户公共类
	 * 
	 * @param request
	 * @return
	 */
	public static SysUser getSysUser(HttpSession session) {
		SysUser sysUser = (SysUser) session.getAttribute(SessionContants.USER_LOGIN_KEY);
		return sysUser;
	}

	/**
	 * 通过用户id获取用户没有拥有的权限
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> getNotPermission() {
		HttpSession session = SpringRequest.getRequest().getSession();
		return (HashSet<String>) session.getAttribute(SessionContants.USER_PERMISSION_NOT_CODE);
	}
	
	/**
	 * 通过用户id获取用户没有拥有的权限
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Set<String> getPermission() {
		HttpSession session = SpringRequest.getRequest().getSession();
		return (HashSet<String>) session.getAttribute(SessionContants.USER_PERMISSION_CODE);
	}

}
