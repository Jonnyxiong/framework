package com.jsmsframework.redis;

import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

/**
 * Redis   String结构操作
 *
 * @outhor tanjiangqiang
 * @create 2017-11-13 15:31
 */
@Service
public class StringRedis  extends JsmsRedis {
    /**
     * 保存数据到redis中
     *
     * @param key
     * @param value
     * @return 失败返回null
     */
    public String stringSet(final String key, final String value) {
        return super.execute(new Function<ShardedJedis, String>() {
            @Override
            public String execute(ShardedJedis shardedJedis) {
                return shardedJedis.set(key, value);
            }

        });
    }

    /**
     * 保存数据到redis中并设置生存时间，生存时间单位是：秒
     *
     * @param key
     * @param value
     * @param seconds
     * @return 失败返回null
     */
    public String putAndExpire(final String key, final String value, final Integer seconds) {
        return super.execute(new Function<ShardedJedis, String>() {
            @Override
            public String execute(ShardedJedis shardedJedis) {
                String result = shardedJedis.set(key, value);
                shardedJedis.expire(key, seconds);//设置生存时间
                return result;
            }

        });
    }

    /**
     * 从redis中获取数据
     *
     * @param key
     * @return 失败返回null
     */
    public String get(final String key) {
        return super.execute(new Function<ShardedJedis, String>() {
            @Override
            public String execute(ShardedJedis shardedJedis) {
                return shardedJedis.get(key);
            }

        });
    }

    /**
     * 设置key生存时间，单位：秒
     *
     * @param key
     * @param seconds
     * @return 失败返回null
     */
    public Long expire(final String key, final Integer seconds) {
        return super.execute(new Function<ShardedJedis, Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                return shardedJedis.expire(key, seconds);
            }

        });
    }

    /**
     * 从redis中删除数据
     *
     * @param key
     * @return 失败返回null
     */
    public Long del(final String key) {
        return super.execute(new Function<ShardedJedis, Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                return shardedJedis.del(key);
            }
        });
    }
}