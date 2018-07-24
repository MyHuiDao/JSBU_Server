package com.huidao.common.cache.redis;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisCache {
	private static final int lockTimeOut = 10; // 锁超时
	private static final int lockRetryTime = 10; // 重新请求锁
	private static final String lock_key = "lock_";
	private static final int forever_time = 365 * 24 * 60 * 60;

	static {
		getPool();
	}
	private static JedisPool pool = null;

	public synchronized static JedisPool getPool() {
		if (pool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
			// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
			config.setMaxTotal(RedisConfig.getRedisMaxTotal());
			// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
			config.setMaxIdle(RedisConfig.getRedisMaxIdle());
			// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			config.setMaxWaitMillis(RedisConfig.getRedisMaxWaitMillis());
			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(true);
			pool = new JedisPool(config, RedisConfig.getRedisUrl(), RedisConfig.getRedisPort(),
					RedisConfig.getRedisTimeOut(), RedisConfig.getRedisPassWord());
		}
		return pool;
	}

	public static Jedis getJedis() {
		pool = getPool();
		return pool.getResource();
	}

	/**
	 * 获取 string
	 * 
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		Jedis jedis = getJedis();
		String j = jedis.get(key);
		jedis.close();
		if ("null".equals(j)) {
			return null;
		}
		return j;
	}

	/**
	 * 删除缓存
	 * 
	 * @param k
	 */
	public static void remove(String k) {
		Jedis jedis = getJedis();
		jedis.del(k);
		jedis.close();
	}

	/**
	 * 重置时间
	 * 
	 * @param k
	 * @param time
	 */
	public static void resetTime(String k, int time) {
		Jedis jedis = getJedis();
		jedis.expire(k, time);
		jedis.close();
	}

	/**
	 * 设置缓存对象 ，采用json
	 * 
	 * @param k
	 * @param v
	 * @param time
	 *            秒
	 */
	public static void set(String k, Object v, int time) {
		Jedis jedis = getJedis();
		if (v instanceof String) {
			jedis.set(k, v.toString());
		} else {
			jedis.set(k, JSONObject.toJSONString(v));
		}
		jedis.expire(k, time);
		jedis.close();
	}

	/**
	 * * 设置缓存对象 ，采用json
	 * 
	 * @param k
	 * @param v
	 */
	public static void set(String k, Object v) {
		Jedis jedis = getJedis();
		if (v instanceof String) {
			jedis.set(k, v.toString());
		} else {
			jedis.set(k, JSONObject.toJSONString(v));
		}
		jedis.expire(k, forever_time);
		jedis.close();
	}

	/**
	 * * 设置缓存对象 ，采用json
	 * 
	 * @param k
	 * @param v
	 */
	public static void setHset(String key, String k, Object v) {
		Jedis jedis = getJedis();
		if (v instanceof String) {
			jedis.hset(key, k, v.toString());
		} else {
			jedis.hset(key, k, JSONObject.toJSONString(v));
		}
		jedis.close();
	}

	/**
	 * json转对象
	 * 
	 * @param k
	 * @param clazz
	 * @return
	 */
	public static <T> T get(String k, Class<T> clazz) {
		String jsonStr = get(k);
		return JSONObject.parseObject(jsonStr, clazz);
	}

	public static void lock(String key) {
		Jedis jedis = getJedis();
		int i = 0;
		while (true) {
			Long v = jedis.setnx(lock_key + key, "true");
			jedis.expire(lock_key + key, lockTimeOut);
			if (v == 0) {
				i++;
				if (i * lockRetryTime > lockTimeOut) {
					unlock(key);
					throw new RuntimeException("lock time out");
				}
				try {
					Thread.sleep(lockRetryTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
		}
		jedis.close();
	}

	public static int getLock(String key) {
		Jedis jedis = getJedis();
		Long v = jedis.setnx(lock_key + key, "true");
		jedis.close();
		return Integer.valueOf(v + "");

	}

	public static void unlock(String key) {
		// System.err.println("解锁》》》》》》》》》》》》》》》》》》》》》》》》》"+key);
		remove(lock_key + key);
	}

	public static Map<String, String> hGetAll(String key) {
		Map<String, String> mapAll = new HashMap<String, String>();
		Jedis jedis = getJedis();
		mapAll = jedis.hgetAll(key);
		jedis.close();
		return mapAll;
	}

	public static <T> T hGet(String key, String k, Class<T> clazz) {
		Jedis jedis = getJedis();
		String j = jedis.hget(key, k);
		jedis.close();
		return JSONObject.parseObject(j, clazz);
	}

	public static String hGet(String key, String k) {
		Jedis jedis = getJedis();
		String j = jedis.hget(key, k);
		jedis.close();
		return j;
	}

	public static void hDel(String key, String k) {
		Jedis jedis = getJedis();
		jedis.hdel(key, k);
		jedis.close();
	}

}
