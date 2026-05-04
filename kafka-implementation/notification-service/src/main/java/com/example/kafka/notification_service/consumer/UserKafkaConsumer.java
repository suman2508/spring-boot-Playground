package com.example.kafka.notification_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserKafkaConsumer {

    @KafkaListener(topics = "${kafka.topic.user-random-topic}")
    public void hanldeUserRandomTopic1(String message){
        log.info("message recieved hanldeUserRandomTopic1: {}", message);
    }

    @KafkaListener(topics = "${kafka.topic.user-random-topic}")
    public void hanldeUserRandomTopic2(String message){
        log.info("message recieved hanldeUserRandomTopic2: {}", message);
    }

    @KafkaListener(topics = "${kafka.topic.user-random-topic}")
    public void hanldeUserRandomTopic3(String message){
        log.info("message recieved hanldeUserRandomTopic3: {}", message);
    }

}
