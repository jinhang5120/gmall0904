package com.jh.gmall;

import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.JedisPool;

@SpringBootTest
public class Test1 {
    @Autowired
    JedisPool jedisPool;
    @Test
    public void m1(){
        System.out.println("abccccccccccccccccc");
        System.out.println("jedisPool = " + jedisPool);
        System.out.println("jedisPool.getResource() = " + jedisPool.getResource());
//        System.out.println("jedisPool.getResource() = " + jedisPool.getResource());
        System.out.println("jedisPool.getResource().get(\"key1\") = " + jedisPool.getResource().get("key1"));
    }

    @Autowired
    RedissonClient redissonClient;
    @Test
    public void m2(){
        System.out.println("redissonClient = " + redissonClient);
    }
}
