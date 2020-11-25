package com.jh.gmall.config;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration //之所以从配置文件中取值，是因为不同模块都要用jedis pool但是要读不同的配置文件
//每个模块配置自己的配置文件
@Slf4j
public class Config1 {
    @Value("${spring.redis.host:10.27.100.113}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private String port;
    @Value("${spring.redis.password:123456}")
    private String password;
    @Value("${spring.redis.jedis.pool.max-active:200}")
    private String maxActive;
    @Value("${spring.redis.jedis.pool.max-idle:100}")
    private String maxIdle;
    @Value("${spring.redis.jedis.pool.min-idle:100}")
    private String minIdle;
    @Value("${spring.redis.timeout:20000}")
    private String timeout;
    @Bean
    public JedisPool jedisPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(Integer.valueOf(maxIdle));
        jedisPoolConfig.setMinIdle(Integer.valueOf(minIdle));
        jedisPoolConfig.setMaxTotal(Integer.valueOf(minIdle));

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, Integer.valueOf(port), Integer.valueOf(timeout), password);
        LoggerFactory.getLogger(this.getClass()).info("jedisPool连接成功： "+host+"\t"+port);
        return jedisPool;
    }

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+host+":"+port).setPassword(password);
        return Redisson.create(config);
    }

    @Value("${elasticsearch.server.uri:http://10.27.100.113:9200}")
    private String serverUri;
    @Bean
    public JestClient jestClient(){
        JestClientFactory jestClientFactory = new JestClientFactory();
        HttpClientConfig.Builder builder = new HttpClientConfig.Builder(serverUri);
        builder.multiThreaded(true);
        jestClientFactory.setHttpClientConfig(builder.build());
        return jestClientFactory.getObject();
    }
}
