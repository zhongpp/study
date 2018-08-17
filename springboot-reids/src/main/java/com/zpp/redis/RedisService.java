package com.zpp.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author pingpingZhong
 *         Date: 2017/8/28
 *         Time: 18:02
 */
@Service
public class RedisService implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ThreadPoolExecutorFactoryBean threadPoolExecutorFactoryBean;

    public void operateList() {
        String key = "first_name";
//        System.out.println(redisTemplate.opsForList().leftPop(key));
        Long length = stringRedisTemplate.opsForList().size(key);
        System.out.println(length);
        System.out.println(stringRedisTemplate.opsForList().range(key, 0, length));
        stringRedisTemplate.expire(key, 30, TimeUnit.SECONDS);
        List<String> timeStamps = stringRedisTemplate.opsForList().range(key, 0, length);
        System.out.println(stringRedisTemplate.opsForList().leftPush(key, "name1"));
        System.out.println(stringRedisTemplate.opsForList().leftPush(key, "name2"));
        System.out.println(stringRedisTemplate.opsForList().leftPush(key, "name3"));
        Long length2 = stringRedisTemplate.opsForList().size(key);
        List<String> timeStamps2 = stringRedisTemplate.opsForList().range(key, 0, length2);
        System.out.println(StringUtils.join(timeStamps2, ","));

        //TTL 查看key的过期时间
        stringRedisTemplate.opsForValue().set("name", "zhongpingping", 30, TimeUnit.SECONDS);

    }

    @Override
    public void run(String... args) throws Exception {
        excutePoolTestRedis();
    }


    public void countDownLatchTestRedis() {
        int threadNum = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        Runnable runnable = () -> {
            try {
                countDownLatch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            long startTime = System.currentTimeMillis();
            stringRedisTemplate.opsForValue().get("name");
            System.out.println(System.currentTimeMillis() - startTime);
        };

        for (int i = 0; i < threadNum; i++) {
            new Thread(runnable).start();
            countDownLatch.countDown();
        }
    }

    public void excutePoolTestRedis() {
        Executor executor = threadPoolExecutorFactoryBean.getObject();
        for (int i = 0; i < 100; i++) {
            executor.execute(() -> {
                long startTime = System.currentTimeMillis();
                stringRedisTemplate.opsForValue().get("name");
                System.out.println(System.currentTimeMillis() - startTime);
            });
        }
    }

}
