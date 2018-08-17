package com.zpp.springboot.start.initializers;

import com.zpp.springboot.start.bean.SimpleBean;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by Format
 * on 2017/5/1.
 */
public class SimpleApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (applicationContext instanceof AnnotationConfigApplicationContext) {
            ((AnnotationConfigApplicationContext) applicationContext).getBeanFactory().registerSingleton("testBean", new SimpleBean("id-001", "created by initializer"));
        }
    }
}
