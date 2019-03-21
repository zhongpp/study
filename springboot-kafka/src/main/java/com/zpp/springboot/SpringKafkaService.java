package com.zpp.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author pingpingZhong
 * Date: 2017/7/25
 * Time: 18:11
 */

@Service
public class SpringKafkaService implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringKafkaService.class);

    private static String HELLOWORLD_TOPIC = "REQUESTQ";

    @Autowired
    private Sender sender;

    @Autowired
    private Receiver receiver;

    @Override
    public void run(String... args) throws Exception {

        int i = 0;
        while (true) {
            Thread.currentThread().sleep(10000);
            LOGGER.info("Hello Spring Kafka! %i", i);
            sender.send(HELLOWORLD_TOPIC, "Hello Spring Kafka!" + i);
            i++;
        }

//        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);


    }

}
