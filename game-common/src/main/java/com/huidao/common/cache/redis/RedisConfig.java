package com.huidao.common.cache.redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RedisConfig {
	
	private static Properties pp;
	/**
	 * redisUrl连接
	 * @return
	 */
	public static String getRedisUrl(){
		return getPp().getProperty("redis.url");
	}
	
	/**
	 * redis端口
	 */
	public static Integer getRedisPort(){
		return Integer.valueOf(getPp().getProperty("redis.port"));
	}
	/**
	 * 最大总数连接
	 */
	public static Integer getRedisMaxTotal(){
		return Integer.valueOf(getPp().getProperty("redis.maxTotal"));
	}
	/**
	 * redis密码
	 */
	public static String getRedisPassWord(){
		return getPp().getProperty("redis.password");
	}
	
	/**
	 * redis最大等待时间
	 * @return
	 */
	public static Long getRedisMaxWaitMillis(){
		return Long.valueOf(getPp().getProperty("redis.maxWaitMillis"));
	}
	/**
	 * redis最大空闲对象
	 */
	public static Integer getRedisMaxIdle(){
		return Integer.valueOf(getPp().getProperty("redis.maxIdle"));
	}
	
	/**
	 * redis超时时间设置
	 * @return
	 */
	public static Integer getRedisTimeOut(){
		return Integer.valueOf(getPp().getProperty("redis.timeOut"));
	}

	
	private static Properties getPp() {
		if(pp==null){
			pp = readProperties(RedisConfig.class,"/redis.properties");
		}
		return pp;
	}

	private static Properties readProperties(Class<?> clazz,String url){
		Properties pp = new Properties();
		InputStream inStream =clazz.getResourceAsStream(url);
		try {
			pp.load(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pp;
	}
	
}
