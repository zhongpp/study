package com.zpp.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author pingpingZhong
 *         Date: 2017/9/16
 *         Time: 14:31
 */
public class RedisDemo {

    static JedisCommands jedisCommands;
    static JedisPool jedisPool;
    static JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    static String ip = "localhost";
    static int port = 6379;
    static int timeout = 2000;
    {
        init();
    }

    public static void init() {
        // ��ʼ��jedis
        // ��������
        jedisPoolConfig.setMaxTotal(1024);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMaxWaitMillis(100);
        jedisPoolConfig.setTestOnBorrow(false);
        //jedis ��һ������ʱ���ᱨ�� jedisPoolConfig.setTestOnReturn(true);
        // ��ʼ��JedisPool
        jedisPool = new JedisPool(jedisPoolConfig, ip, port, timeout);
        Jedis jedis = jedisPool.getResource();
        jedisCommands = jedis;
    }

    public void setValue(String key, String value) {
        this.jedisCommands.set(key, value);
    }

    public String getValue(String key) {
        return this.jedisCommands.get(key);
    }

    public static void main(String[] args) throws Exception {

        RedisDemo testJedis = new RedisDemo();
        testJedis.setValue("testJedisKey", "testJedisValue");

        int i=0;
        while(true) {
            try{
                jedisCommands.set("k"+i, "v"+i);
                System.out.println(jedisCommands.get("k"+i));
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                jedisPool.close();//�����ӳء��黹����Դ��ǧ��Ҫ���ǡ�
            }
            i++;
            Thread.sleep(5000);
        }

    }


}
