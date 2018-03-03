package com.jsmsframework.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @Description: reids接口
 * @Author: tanjiangqiang
 * @Date: 2017/11/13 - 11:09
 */
@Service
public class JsmsRedis {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsmsRedis.class);

    //有的工程需要，有的工程不需要。设置required=false，有就注入，没有就不注入。
    @Autowired(required = false)
    private ShardedJedisPool shardedJedisPool;



    public <T> T execute(Function<ShardedJedis, T> function) {
        ShardedJedis shardedJedis = null;
        try {

            // 从连接池中获取到jedis分片对象
            shardedJedis = shardedJedisPool.getResource();
            return function.execute(shardedJedis);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("执行Redis出错了------------------------{}"+e.getMessage());
        } finally {
            if (null != shardedJedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                shardedJedis.close();
            }
        }
        return null;
    }




}
