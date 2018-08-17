package com.zpp.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class EventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    private static final int TIMEOUT = 10000;

    private final List<String> topics;
    private KafkaConsumer<String, String> consumer;
    private static ObjectMapper mapper = new ObjectMapper();

    public EventConsumer(String servers, String topic, String consumerGroup) {
        this(servers, Arrays.asList(topic), consumerGroup);
    }

    public EventConsumer(String servers, List<String> topics, String consumerGroup) {
        Properties props = new Properties();
        try {
            props.load(new ClassPathResource("consumer.properties").getInputStream());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        props.put("bootstrap.servers", servers);
        props.put("group.id", consumerGroup);
        this.topics = topics;
        this.consumer = new KafkaConsumer<>(props);
        this.consumer.subscribe(this.topics);
    }

    public ConsumerRecords<String, String> fetchRecords() {
        return this.consumer.poll(TIMEOUT);
    }

    public void commitSync() {
        this.consumer.commitSync();
    }

    public ConsumerRecords<String, String> fetchRecords(int timeout) {
        return this.consumer.poll(timeout);
    }

    public static void main(String[] args) {
        System.out.println("==============消费者===============");
        EventConsumer eventConsumer = new EventConsumer("192.168.20.17:9092,192.168.20.18:9092,192.168.20.19:9092", "kafka", "test");
        while (true) {
            try {
                ConsumerRecords<String, String> records = eventConsumer.fetchRecords();
                for (ConsumerRecord<String, String> r : records) {
                    JsonNode event = mapper.readTree(r.value());
                    String index = event.path("index").asText();
                    System.out.println(index);

                }
                eventConsumer.commitSync();
            } catch (Exception e) {
                logger.error(e.getClass().getName(), e);
            }
        }
    }
}
