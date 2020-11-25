package com.jh.gmall.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
public class RedissonTestController {
    @Autowired
    JedisPool jedisPool;
    @Autowired
    RedissonClient redissonClient;
    @RequestMapping("redissonTest")
    public String m1(){
        RLock lock = redissonClient.getLock("lock");//默认得到可重入锁
        lock.lock();
        try (Jedis jedis = jedisPool.getResource()) {
            String v = jedis.get("k1");
            if (v == null) {
                v = "1";
            }
            System.out.println("v = " + v);
            jedis.set("k1", (Integer.parseInt(v) + 1) + "");
        } finally {
            lock.unlock();
        }
        return "success";
    }
}
