package com.zpp.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.stream.IntStream;

/**
 * @author pingpingZhong
 * Date: 2017/11/23
 * Time: 16:26
 */
public class RedisUtil {

    private static JedisPool pool = new JedisPool(new JedisPoolConfig(), "redis");

    public static void set(String key, String value, long expires) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.set(key, value, "NX", "EX", expires);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            IntStream.range(0, 10000).forEach(i -> {
                RedisUtil.set("key" + i, "value" + i, 1800);
            });
        }
    }


}
