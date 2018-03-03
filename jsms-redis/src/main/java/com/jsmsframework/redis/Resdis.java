package com.jsmsframework.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.exceptions.JedisException;

/**
 * 动态选择DB存储
 *
 * @outhor tanjiangqiang
 * @create 2018-01-11 10:56
 */
public class Resdis {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsmsRedis.class);

    public <T> T execute(Function<Jedis, T> function, JedisShardInfo info, int dbIndex) {
        Jedis jedis = null;
        try {
            if (dbIndex < 0) {
                throw new IllegalArgumentException("Redis指定数据库错误");
            }
            jedis = new Jedis(info);
            jedis.select(dbIndex);
            LOGGER.debug("执行Redis地址,host:" + info.getHost() + ",post:" + info.getPort() + ",db:" + dbIndex);
            return function.execute(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("执行Redis出错了------------------------{}",e.getMessage());
        } finally {
            if (null != jedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                jedis.close();
            }
        }
        throw new JedisException("执行Redis出错了,请检查参数");
    }

}