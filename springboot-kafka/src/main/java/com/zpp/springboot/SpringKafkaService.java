package com.zpp.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author pingpingZhong
 *         Date: 2017/7/25
 *         Time: 18:11
 */

@Service
public class SpringKafkaService implements CommandLineRunner {

    private static String HELLOWORLD_TOPIC = "helloworld.t";

    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;

    @Override
    public void run(String... args) throws Exception {

        sender.send(HELLOWORLD_TOPIC, "Hello Spring Kafka!");

        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);


    }

}
