package com.zpp.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class RedisCacheConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheConfig.class);

    @Value("${spring.redis.host}")
    private String REDIS_HOST;

    @Value("${spring.redis.port}")
    private int REDIS_PORT;

    @Value("${spring.redis.default-expire}")
    private int DEFAULT_CACHE_EXPIRE;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(50);
        config.setMaxIdle(20);
        config.setMaxWaitMillis(10 * 1000);
        config.setTimeBetweenEvictionRunsMillis(10 * 1000);
        config.setMinEvictableIdleTimeMillis(10 * 1000);
        config.setTestWhileIdle(true);
        config.setTestOnBorrow(true);
        config.setTestOnCreate(true);
        config.setTestOnReturn(true);

        JedisConnectionFactory connFactory = new JedisConnectionFactory();
        connFactory.setHostName(REDIS_HOST);
        connFactory.setPort(REDIS_PORT);
        connFactory.setPoolConfig(config);
        return connFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory cf) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(cf);
        return stringRedisTemplate;
    }


    @Bean
    public ThreadPoolExecutorFactoryBean initExecutorFactory() {
        ThreadPoolExecutorFactoryBean threadPoolExecutorFactoryBean = new ThreadPoolExecutorFactoryBean();
        threadPoolExecutorFactoryBean.setCorePoolSize(20);
        threadPoolExecutorFactoryBean.setMaxPoolSize(40);
        threadPoolExecutorFactoryBean.setQueueCapacity(1000);
        threadPoolExecutorFactoryBean.setKeepAliveSeconds(5);
        threadPoolExecutorFactoryBean.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return threadPoolExecutorFactoryBean;
    }

}
