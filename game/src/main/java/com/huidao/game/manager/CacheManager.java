package com.huidao.game.manager;

import com.huidao.common.cache.manager.CacheContants;
import com.huidao.common.cache.redis.RedisCache;
import com.huidao.common.entity.User;
import com.huidao.common.spring.SpringBeanUtil;
import com.huidao.game.config.SystemConfig;

/**
 * 缓存管理类
 * 
 * @author lenovo
 *
 */
public class CacheManager {
	private static SystemConfig systemConfig;

	private static SystemConfig getSystemConfig() {
		if (systemConfig == null) {
			systemConfig = (SystemConfig) SpringBeanUtil.getBean(SystemConfig.class);
		}
		return systemConfig;
	}

	public static User getUser(String token) {
		String userId=getUserIdByToken(token);
		return RedisCache.get(CacheContants.user_id_user + userId, User.class);
	}

	public static String getTokenByUserId(String userId) {
		return RedisCache.get(CacheContants.user_id_token + userId);
	}

	public static String getUserIdByToken(String token) {
		return RedisCache.get(CacheContants.token_user_id + token);
	}

	public static void removeToken(String oldToken) {
		RedisCache.remove(CacheContants.token_user_id + oldToken);
	}

	public static void saveUserToken(String token, User user) {
		RedisCache.set(CacheContants.token_user_id + token, user.getId());
		RedisCache.set(CacheContants.user_id_token + user.getId(), token);
		RedisCache.set(CacheContants.user_id_user + user.getId(), user);
		RedisCache.resetTime(CacheContants.token_user_id + token, getSystemConfig().getTokenTime());
		RedisCache.resetTime(CacheContants.user_id_token + user.getId(), getSystemConfig().getTokenTime());
		RedisCache.resetTime(CacheContants.user_id_user + user.getId(), getSystemConfig().getTokenTime());
	}

	public static void resetTimeUser(String userId) {
		String token = getTokenByUserId(userId);
		RedisCache.resetTime(CacheContants.token_user_id + token, getSystemConfig().getTokenTime());
		RedisCache.resetTime(CacheContants.user_id_token + userId, getSystemConfig().getTokenTime());
		RedisCache.resetTime(CacheContants.user_id_user + userId, getSystemConfig().getTokenTime());
	}

	public static void removeUser(String token) {
		String userId = getUserIdByToken(token);
		RedisCache.resetTime(CacheContants.token_user_id + token, 0);
		RedisCache.resetTime(CacheContants.user_id_token + userId, 0);
		RedisCache.resetTime(CacheContants.user_id_user + userId, 0);
	}
	
	public static void updateUser(User user) {
		RedisCache.set(CacheContants.user_id_user + user.getId(), user);
	}

	public static String getSettingValue(String key) {
		return RedisCache.get(CacheContants.game_setting+key);
	}

	public static void setSettingValue(String key, String value) {
		RedisCache.set(CacheContants.game_setting+key, value);
	}

}
