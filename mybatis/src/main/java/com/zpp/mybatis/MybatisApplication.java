package com.zpp.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * @author pingpingZhong
 *         Date: 2017/7/31
 *         Time: 10:26
 */
@SpringBootApplication
public class MybatisApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MybatisApplication.class, args);
        String[] beanNames = context.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
}
