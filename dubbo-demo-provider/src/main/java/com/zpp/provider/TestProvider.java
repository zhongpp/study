package com.zpp.provider;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.zpp.common.UserServiceBo;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author pingpingZhong
 * Date: 2018/4/4
 * Time: 14:41
 */
public class TestProvider {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context-provider.xml");
        System.out.println("begin");
        System.out.println(((UserServiceBo) context.getBean("userService", UserServiceBo.class)).sayHello("hello"));
        Thread.currentThread().join();


    }

    public static void test() throws Exception {
        //服务实现
        UserServiceBo userServiceBo = new UserServiceImp();

        //当前应用配置
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubboProvider");

        //连接注册中心配置，地址与协议分开配置
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("192.168.20.17:2181");
        registryConfig.setProtocol("zookeeper");

        //服务提供者协议配置
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20880);

        //注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口
        //服务提供者暴露服务配置
        ServiceConfig<UserServiceBo> serviceConfig = new ServiceConfig<>();
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setProtocol(protocolConfig);
        serviceConfig.setInterface(UserServiceBo.class);
        serviceConfig.setRef(userServiceBo);
        serviceConfig.setVersion("1.0.0");
        serviceConfig.setGroup("dubbo");

        //暴露及注册服务
        serviceConfig.export();

        //挂起当前线程
        Thread.currentThread().join();


    }

}
