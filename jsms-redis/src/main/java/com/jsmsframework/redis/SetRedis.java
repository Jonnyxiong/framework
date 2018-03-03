package com.jsmsframework.redis;

import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import java.util.Set;

/**
 * Redis   Set结构操作
 *
 * @outhor tanjiangqiang
 * @create 2017-11-13 16:12
 */
@Service
public class SetRedis extends JsmsRedis {
    /**
     *向set集合中加入元素
     *
     * @param key set的名称
     * @param value 插入的值
     * @return 失败返回null
     */
    public Long put(final String key, final String value) {
        return super.execute(new Function<ShardedJedis, Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                return shardedJedis.sadd(key,value);
            }
        });
    }

    /**
     *获得set集合的所有元素
     *
     * @param key set的名称
     * @return 失败返回null
     */
    public Set<String> getAllSet(final String key) {
        return super.execute(new Function<ShardedJedis, Set<String>>() {
            @Override
            public Set<String> execute(ShardedJedis shardedJedis) {
                return shardedJedis.smembers(key);
            }
        });
    }

    /**
     *删除set集合中的某个元素
     *
     * @param key set的名称
     * @param value 删除的值
     * @return 失败返回null
     */
    public Long del(final String key, final String value)
    {
        return super.execute(new Function<ShardedJedis, Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                return shardedJedis.srem(key,value);
            }
        });
    }

    /**
     * 设置set集合的有效时间
     *
     * @param key set的名称
     * @param second set的有效时间,单位为秒
     * @return 失败返回null
     */
    public Long expire(final String key, final int second) {
        return super.execute(new Function<ShardedJedis, Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                return shardedJedis.expire(key, second);
            }
        });
    }
}