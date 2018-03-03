package com.jsmsframework.redis.service;

import com.jsmsframework.common.util.JsonUtil;
import com.jsmsframework.redis.Function;
import com.jsmsframework.redis.Resdis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

/**
 * Redis 实现类
 *
 * @outhor tanjiangqiang
 * @create 2018-01-11 11:53
 */
@Service("redisService")
public class RedisServiceImpl extends Resdis implements RedisService{

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Override
    public String stringSet(JedisShardInfo info, final String key, final String value, int dbIndex) {
        return super.execute(new Function<Jedis, String>() {
            @Override
            public String execute(Jedis jedis) {
                LOGGER.debug("存储的key:" + key + ",存储的value:" + value);
                return jedis.set(key, value);
            }
        }, info, dbIndex);
    }

    @Override
    public String putAndExpire(JedisShardInfo info, final String key, final String value, final Integer seconds, int dbIndex) {
        return super.execute(new Function<Jedis, String>() {
            @Override
            public String execute(Jedis jedis) {
                String result = jedis.set(key, value);
                //设置生存时间
                jedis.expire(key, seconds);
                LOGGER.debug("存储的key:" + key + ",存储的value:" + value + ",生存时间的seconds:" + JsonUtil.toJson(seconds));
                return result;
            }
        }, info, dbIndex);
    }

    @Override
    public String get(JedisShardInfo info, final String key, int dbIndex) {
        return super.execute(new Function<Jedis, String>() {
            @Override
            public String execute(Jedis jedis) {
                LOGGER.debug("获取的key:" + key);
                return jedis.get(key);
            }
        }, info, dbIndex);
    }

    @Override
    public Long expire(JedisShardInfo info, final String key, final Integer seconds, int dbIndex) {
        return super.execute(new Function<Jedis, Long>() {
            @Override
            public Long execute(Jedis jedis) {
                LOGGER.debug("设置key的生存时间key:" + key + ",生存时间seconds:" + JsonUtil.toJson(seconds));
                return jedis.expire(key, seconds);
            }
        }, info, dbIndex);
    }

    @Override
    public Long del(JedisShardInfo info, final String key, int dbIndex) {
        return super.execute(new Function<Jedis, Long>() {
            @Override
            public Long execute(Jedis jedis) {
                LOGGER.debug("删除key:" + key);
                return jedis.del(key);
            }
        }, info, dbIndex);
    }
}