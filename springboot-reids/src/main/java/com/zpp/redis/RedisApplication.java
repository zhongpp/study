package com.zpp.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * @author pingpingZhong
 *         on 2017/5/23.
 */
@SpringBootApplication
public class RedisApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RedisApplication.class, args);
        String[] beanNames = context.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            //System.out.println(beanName);
        }
    }
}
