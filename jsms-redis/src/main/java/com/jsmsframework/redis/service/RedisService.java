package com.jsmsframework.redis.service;

import redis.clients.jedis.JedisShardInfo;

/**
 * Redis 接口
 *
 * @outhor tanjiangqiang
 * @create 2018-01-11 11:47
 */
public interface RedisService {

    /**
     * 保存数据到redis中
     *
     * @param info 连接Resdis信息
     * @param key 存储的key
     * @param value 存储的value
     * @param dbIndex 存储的DB
     * @return 失败返回null
     */
    String stringSet(JedisShardInfo info , String key, String value, int dbIndex);

    /**
     * 保存数据到redis中并设置生存时间，生存时间单位是：秒
     *
     * @param info 连接Resdis信息
     * @param key
     * @param value
     * @param seconds
     * @param dbIndex 存储的DB
     * @return 失败返回null
     */
    String putAndExpire(JedisShardInfo info, String key, String value, Integer seconds, int dbIndex);

    /**
     * 从redis中获取数据
     *
     * @param info 连接Resdis信息
     * @param key
     * @param dbIndex 存储的DB
     * @return 失败返回null
     */
    String get(JedisShardInfo info, String key, int dbIndex);

    /**
     * 设置key生存时间，单位：秒
     *
     * @param info 连接Resdis信息
     * @param key
     * @param seconds
     * @param dbIndex 存储的DB
     * @return 失败返回null
     */
    Long expire(JedisShardInfo info, String key, Integer seconds, int dbIndex);

    /**
     * 从redis中删除数据
     *
     * @param info 连接Resdis信息
     * @param key
     * @param dbIndex 存储的DB
     * @return 失败返回null
     */
    Long del(JedisShardInfo info, String key, int dbIndex);
}