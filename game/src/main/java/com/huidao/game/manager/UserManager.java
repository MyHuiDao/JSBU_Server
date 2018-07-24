package com.huidao.game.manager;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.huidao.common.entity.User;
import com.huidao.common.exception.UserNoLoginException;
import com.huidao.common.spring.SpringRequest;

public class UserManager {
	
	public static String getCurrentToken() {
		HttpServletRequest requst = SpringRequest.getRequest();
		String token = requst.getHeader("token");
		if (StringUtils.isBlank(token)) {
			token=requst.getParameter("token");
			if(StringUtils.isBlank(token)) {				
				throw new UserNoLoginException("用户没有登录");
			}
		}
		return token;
	}

	/**
	 * 用userId 获取token
	 * @param userId
	 * @return
	 */
	public static String getTokenByUserId(String userId) {
		return CacheManager.getTokenByUserId(userId);
	}


	public static void removeToken(String oldToken) {
		CacheManager.removeToken(oldToken);
	}

	public static void saveUserToken(String token, User user) {
		CacheManager.saveUserToken(token,user);
	}
}
