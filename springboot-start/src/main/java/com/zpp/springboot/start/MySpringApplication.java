package com.zpp.springboot.start;

import com.zpp.springboot.start.bean.SimpleBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by Format
 * on 2017/5/1.
 */
@SpringBootApplication
public class MySpringApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(MySpringApplication.class, args);
        SimpleBean sb = applicationContext.getBean("testBean", SimpleBean.class);
        System.out.println("id: " + sb.getId() + ", name: " + sb.getName());
    }
}
