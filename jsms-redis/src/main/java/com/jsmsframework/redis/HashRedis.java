package com.jsmsframework.redis;

import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import java.util.Map;

/**
 * Redis   Hash结构操作
 *
 * @outhor tanjiangqiang
 * @create 2017-11-13 15:30
 */
@Service
public class HashRedis extends JsmsRedis {

    /**
     * 向名称为key的hash中添加元素fied————value
     *
     * @param key   标示创建的hash
     * @param field hash中的key
     * @param value 与field对应的value值
     * @return 失败返回null
     */
    public Long put(final String key, final String field, final String value) {
        return super.execute(new Function<ShardedJedis, Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                return shardedJedis.hset(key, field, value);
            }
        });
    }

    /**
     * 返回名称为key的hash元素
     *
     * @param key   标示一个hash表
     * @return 失败返回null
     */
    public Map<String, String> getHashAll(final String key) {
        return super.execute(new Function<ShardedJedis, Map<String, String>>() {
            @Override
            public Map<String, String> execute(ShardedJedis shardedJedis) {
                return shardedJedis.hgetAll(key);
            }
        });
    }

    /**
     * 返回名称为key的hash中field对应的value
     *
     * @param key   标示一个hash表
     * @param field hash表中的key
     * @return 失败返回null
     */
    public String get(final String key, final String field) {
        return super.execute(new Function<ShardedJedis, String>() {
            @Override
            public String execute(ShardedJedis shardedJedis) {
                return shardedJedis.hget(key, field);
            }
        });
    }

    /**
     * 删除名称为key的hash中键为value的值
     *
     * @param key   标示hash的名称
     * @param field hash的键值
     * @return 失败返回null
     */
    public Long del(final String key, final String field) {
        return super.execute(new Function<ShardedJedis, Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                return shardedJedis.hdel(key, field);
            }
        });
    }

    /**
     *为名称为key的Hash表设置有效时间
     *
     * @param key 标示hash表
     * @param second 有效时间,秒为单位
     * @return 失败返回null
     */
    public Long expire(final String key,final int second) {
        return super.execute(new Function<ShardedJedis, Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                return shardedJedis.expire(key, second);
            }
        });
    }
}