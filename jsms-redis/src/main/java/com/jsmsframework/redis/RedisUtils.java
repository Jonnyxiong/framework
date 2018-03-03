package com.jsmsframework.redis;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Set;

@Service("redisUtils")
public class RedisUtils {
	private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

	@Autowired
	private JedisPool jedisPool;



	public  Jedis getJedis() {

		Jedis jedis = jedisPool.getResource();
		if (!jedis.isConnected()) {
			logger.error("redis未连接");
			try {
				logger.info("redis尝试连接");
				jedis.connect();
				logger.info("redis连接成功");
			} catch (Exception e) {
				logger.error("redis连接失败");
			}
		}
		return jedis;
	}

	// add
	public  Long add(String key, String... value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			long res = jedis.sadd(key, value);
			logger.info("add key " + key);
			return res;
		} catch (Exception e) {
			logger.error("RedisService.add", e);
			jedisPool.returnBrokenResource(jedis);
			return 0l;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// all members
	public  Set<String> smembers(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Set<String> res = jedis.smembers(key);
			return res;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// is exist
	public  boolean sismember(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			boolean res = jedis.sismember(key, value);
			return res;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// 在指定db中set某一个key
	public  String setSpecificDb(String key, String value, int index) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(index);
			logger.info("select index " + index);
			String res = jedis.set(key, value);
			logger.info("set key " + key + ",value " + value);
			return res;
		} catch (Exception e) {
			logger.error("RedisService.setSpecificDb ", e);
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// 在指定db中get某一个key
	public  String getSpecificDb(String key, int index) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(index);
			logger.info("select index " + index);
			String res = jedis.get(key);
			logger.info("get key " + key);
			return res;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}


	public  Long append(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Long res = jedis.append(key, value);
			return res;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// numbers
	public  Long scard(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Long res = jedis.scard(key);
			return res;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return 0l;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// delete
	public  Long delKey(final String... key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Long res = jedis.del(key);
			logger.info("delete key " + key);
			return res;
		} catch (Exception e) {
			logger.error("RedisService.delKey", e);
			jedisPool.returnBrokenResource(jedis);
			return 0l;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public  Long delKeySpecifiedDb(int index, final String... key) {
		Jedis jedis = null;
		try {

			for (int i = 0; i < key.length; i++) {
				key[i] = key[i].toLowerCase();
			}
			jedis = getJedis();
			jedis.select(index);
			logger.info("select index " + index);
			Long res = jedis.del(key);
			logger.info("delete key " + key);
			return res;
		} catch (Exception e) {
			logger.error("RedisService.delKey", e);
			jedisPool.returnBrokenResource(jedis);
			return 0l;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// set a
	public  String set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String res = jedis.set(key, value);
			logger.info("add key " + key + ",value " + value);
			return res;
		} catch (Exception e) {
			logger.error("RedisService.set", e);
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public  synchronized String getAndSet(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String cur = jedis.get(key);
			long curNum = 0;
			if (StringUtils.isNotEmpty(cur)) {
				curNum = Long.parseLong(cur);
			}
			long nownum = curNum + 1;
			jedis.set(key, String.valueOf(nownum));
			logger.info("getAndSet key " + key);
			return String.valueOf(nownum);
		} catch (Exception e) {
			logger.error("RedisService.getAndSet", e);
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// get a
	public  String get(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String res = jedis.get(key);
			logger.info("get key " + key);
			return res;
		} catch (Exception e) {
			logger.error("RedisService.get", e);
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public  Map<String, String> hgetall(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Map<String, String> res = jedis.hgetAll(key);
			logger.info("hgetall key " + key);
			return res;
		} catch (Exception e) {
			logger.error("RedisService.hgetall", e);
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public  Map<String, String> hgetallSpecificDb(int index, String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(index);
			logger.info("select index " + index);
			Map<String, String> res = jedis.hgetAll(key);
			logger.info("hgetall key " + key);
			return res;
		} catch (Exception e) {
			logger.error("RedisService.hgetall", e);
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public  String hmset(String key, Map<String, String> hash, int seconds) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String res = jedis.hmset(key, hash);
			if (seconds > 0) { // 设置超时时间
				long expireNum = jedis.expire(key, seconds);
				logger.info("超时时间设置：key = " + key + ",seconds = " + seconds + ",expireNum = " + expireNum);
			}
			return res;
		} catch (Throwable e) {
			logger.error("redis的hmset方法错误, key = " + key + ",hash = " + hash + ",异常：", e);
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public  String hmsetSpecifiedDb(int index, String key, Map<String, String> hash, int seconds) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(index);
			logger.info("select index " + index);
			String res = jedis.hmset(key, hash);
			if (seconds > 0) { // 设置超时时间
				long expireNum = jedis.expire(key, seconds);
				logger.info("超时时间设置：key = " + key + ",seconds = " + seconds + ",expireNum = " + expireNum);
			}
			return res;
		} catch (Throwable e) {
			logger.error("redis的hmset方法错误, key = " + key + ",hash = " + hash + ",异常：", e);
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	public  long hset(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			long res = jedis.hset(key, field, value);
			logger.info("hset key " + key);
			return res;
		} catch (Exception e) {
			logger.error("RedisService.hset", e);
			jedisPool.returnBrokenResource(jedis);
			return 0;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// random a
	public  String srandmember(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String res = jedis.srandmember(key);
			return res;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// remove a random
	public  String spop(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String res = jedis.spop(key);
			return res;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// remove a or more member
	public  Long srem(String key, String... members) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Long res = jedis.srem(key, members);
			return res;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return 0l;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// move a member from one to another
	public  Long smove(String srckey, String dstkey, String member) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Long res = jedis.smove(srckey, dstkey, member);
			return res;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return 0l;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// union all
	public  Set<String> sunion(String... keys) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Set<String> res = jedis.sunion(keys);
			return res;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// uoion all keys store to dstkey
	public  Long sunionstore(String dstkey, String... keys) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Long res = jedis.sunionstore(dstkey, keys);
			return res;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return 0l;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// join inner all keys
	public  Set<String> sinter(String... keys) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Set<String> res = jedis.sinter(keys);
			return res;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// inner all keys store to dstkey
	public  Long sinter(String dstkey, String... keys) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Long res = jedis.sinterstore(dstkey, keys);
			return res;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return 0l;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * @param regx
	 * @return Set<String> getKeys
	 */
	public  Set<String> getKeys(String regx) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			Set<String> set = jedis.keys(regx);
			return set;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * @param index
	 * @param regx
	 * @return Set<String> getKeys
	 */
	public  Set<String> getKeysSpecifiedDb(int index, String regx) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(index);
			logger.info("select index " + index);
			Set<String> set = jedis.keys(regx.toLowerCase());
			return set;
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	// set a
	public  String setAndExpire(String key, String value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String res = jedis.setex(key, seconds, value);
			logger.info("setAndExpire key " + key + ",value " + value + ",seconds " + seconds);
			return res;
		} catch (Exception e) {
			logger.error("RedisService.setAndExpire", e);
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 获取redis缓存key
	 *
	 * @param key
	 * @param str
	 * @return String
	 */
	public static String getKey(String key,String str){
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append(key).append(str);
		return sBuffer.toString();
	}

	/**
	 *
	 * @param index
	 * @param key
	 * @param value
	 * @return
	 */
	public  Long lPushSpecificDb(int index, String key, String... value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.select(index);
			logger.info("select index " + index);
			Long res = jedis.lpush(key, value);
			logger.info("hgetall key " + key);
			return res;
		} catch (Exception e) {
			logger.error("RedisUtils.lPushSpecificDb", e);
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 指定db指定key设置超时时间
	 * @param index
	 * @param key
	 * @param expire
	 * @return
	 */
	public  Long expire(Integer index, String key, Integer expire){
		Jedis jedis = null;
		try {
			if(index == null){
				index = 0;
			}
			jedis = getJedis();
			jedis.select(index);
			logger.info("select index " + index);
			Long res = jedis.expire(key, expire);
			logger.info("set key: {} expire: {}", key, expire);
			return res;
		} catch (Exception e) {
			logger.error("RedisUtils.expire", e);
			jedisPool.returnBrokenResource(jedis);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}

}
