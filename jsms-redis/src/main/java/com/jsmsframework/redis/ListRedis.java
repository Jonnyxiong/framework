package com.jsmsframework.redis;

import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import java.util.List;

/**
 * Redis   String结构操作
 *
 * @outhor tanjiangqiang
 * @create 2017-11-13 15:36
 */
@Service
public class ListRedis extends JsmsRedis {


    /**
     * 在名称为key的list头添加一个值为value的元素
     *
     * @param key list的名称
     * @param value 插入的值
     * @return 失败返回null
     */
    public Long putStart(final String key, final String value) {
        return super.execute(new Function<ShardedJedis, Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                return shardedJedis.lpush(key,value);
            }
        });
    }


    /**
     * 在名称为key的list尾添加一个值为value的元素
     *
     * @param key list的名称
     * @param value 插入的值
     * @return 失败返回null
     */
    public Long putEnd(final String key,final String value) {
        return this.execute(new Function<ShardedJedis, Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                return shardedJedis.rpush(key,value);
            }
        });
    }

    /**
     * 在名称为key的list中index位置的元素重新赋值为value
     *
     * @param key list的名称
     * @param index 插入的下标
     * @param value 插入的值
     * @return 失败返回null
     */
    public String putIndex(final String key,final long index,final String value) {
        return super.execute(new Function<ShardedJedis, String>() {
            @Override
            public String execute(ShardedJedis shardedJedis) {
                String result = null;
                    long length = shardedJedis.llen(key);
                    if(shardedJedis.exists(key)&&index<=length&&index>=1){
                        result = shardedJedis.lset(key, index, value);
                    }
                return result;
            }
        });
    }

    /**
     * 为List设置有效时间
     *
     * @param key list的名称
     * @param time list的有效时间
     * @return 失败返回null
     */
    public Long expire(final String key,final int time) {
        return super.execute(new Function<ShardedJedis, Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                Long result = null;
                if(shardedJedis.exists(key)) {
                    result = shardedJedis.expire(key, time);
                }
                return result;
            }
        });
    }

    /**
     * 返回名称为key的list中index位置的元素的值
     *
     * @param key list的名称
     * @param index List的下标值
     * @return 失败返回null
     */
    public String getIndex(final String key,final long index) {
        return super.execute(new Function<ShardedJedis, String>() {
            @Override
            public String execute(ShardedJedis shardedJedis) {
                return shardedJedis.lindex(key, index);
            }
        });
    }

    /**
     * 获得start——end之间的元素值,start默认从1开始,当start=1,end=0时获取全部的元素
     *
     * @param key list的名称
     * @param start 开始下标
     * @param end 结束下标
     * @return 失败返回null
     */
    public List<String> get(final String key, final long start, final long end){
        return super.execute(new Function<ShardedJedis, List<String>>() {
            @Override
            public List<String> execute(ShardedJedis shardedJedis) {
                return shardedJedis.lrange(key, start, end);
            }
        });
    }

    /**
     * 删除名称为key的list中的首元素
     *
     * @param key list的名称
     * @return 失败返回null
     */
    public String delStart(final String key) {
        return super.execute(new Function<ShardedJedis, String>() {
            @Override
            public String execute(ShardedJedis shardedJedis) {
                return shardedJedis.lpop(key);
            }
        });
    }

    /**
     * 删除名称为key的list中的尾元素
     *
     * @param key list的名称
     * @return 失败返回null
     */
    public String delEnd(final String key){
        return super.execute(new Function<ShardedJedis, String>() {
            @Override
            public String execute(ShardedJedis shardedJedis) {
                return shardedJedis.rpop(key);
            }
        });
    }

    /**
     * 删除count个名称为key的List中值为value的元素
     * count为0,删除所有值为value的元素
     * count>0从头到尾count个值为value的元素
     * count<0从尾到头count个值为value的元素
     *
     * @param key list的名称
     * @param count 删除情景标志
     * @param value 删除的value
     * @return 失败返回null
     */
    public Long del(final String key, final long count, final String value){
        return super.execute(new Function<ShardedJedis, Long>() {
            @Override
            public Long execute(ShardedJedis shardedJedis) {
                return shardedJedis.lrem(key, count, value);
            }
        });
    }
}