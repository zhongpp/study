package com.zpp.consumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.zpp.common.UserServiceBo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author pingpingZhong
 *         Date: 2018/4/4
 *         Time: 17:01
 */
public class TestConsumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context-consumer.xml");
        UserServiceBo userServiceBo = (UserServiceBo) context.getBean("userService");
        System.out.println(userServiceBo.sayHello("哈哈哈"));
        Thread.currentThread().join();
    }

    public void test() {
        //当前应用配置
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubboProvider");

        //连接注册中心配置，地址与协议分开配置
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("192.168.20.17:2181");
        registryConfig.setProtocol("zookeeper");

        //注意：ReferenceConfig为重对象，内部封装了与注册中心的连接，以及与服务提供方的连接
        ReferenceConfig<UserServiceBo> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(applicationConfig);
        //多个注册中心可以用setRegistries()
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(UserServiceBo.class);
        referenceConfig.setVersion("1.0.0");
        //和本地bean一样使用xxxService
        //注意：此代理对象内部封装了所有通讯细节
        UserServiceBo userServiceBo = referenceConfig.get();
        System.out.println(userServiceBo.sayHello("哈哈哈"));
    }
}
