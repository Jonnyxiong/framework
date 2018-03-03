package com.jsmsframework.redis;

import com.jsmsframework.redis.service.RedisService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisShardInfo;

/**
 * Redis和spring整合连接测试
 *
 * @outhor tanjiangqiang
 * @create 2017-11-13 14:10
 */
public class RedisSpringTest {

    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-db-redis.xml");
        RedisService jsmsRedis = context.getBean("redisService", RedisService.class);
        JedisShardInfo info = new JedisShardInfo("172.16.5.53",6379);
        jsmsRedis.stringSet(info,"测试", "123",11);
        System.out.println(jsmsRedis);
    }
}