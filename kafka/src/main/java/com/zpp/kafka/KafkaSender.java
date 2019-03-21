package com.zpp.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Properties;

public class KafkaSender {
    private static final Logger logger = LoggerFactory.getLogger(KafkaSender.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    private KafkaProducer<String, String> producer;

    public KafkaSender(String service) {

        Properties props = new Properties();
        try {
            props.load(new ClassPathResource("producer.properties").getInputStream());
        } catch (Exception e) {
            logger.error("load properties failed, :{}", e);
        }
        props.put("bootstrap.servers", service);
        this.producer = new KafkaProducer<>(props);
    }

    public void send(Object data, String topic) {
        try {
            String content = this.objectMapper.writeValueAsString(data);
            this.producer.send(new ProducerRecord<>(topic, content), (metadata, e) -> {
                if (e != null) {
                    logger.error("send to kafka error :", e);
                }
            });
        } catch (Exception e) {
            logger.error("output kafka failed,{}", e);
        }
    }

    public static void main(String[] args) {
        logger.debug("=======生产者=======");
        KafkaSender kafkaSender = new KafkaSender("192.168.6.141:9092");
        int index = 0;
        while (true) {
            HashMap<String, Object> wrapper = new HashMap<>();
            wrapper.put("uuid", "test");
            wrapper.put("target_state", "from zpp");
            wrapper.put("index", Integer.valueOf(index));
            kafkaSender.send(wrapper, "kafka");
            System.out.println(index);
            index++;
            logger.debug("==========");
        }
    }

}
