package com.example.demo.service;

import com.example.kafka.avro.UserAction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AvroConsumerService {
    @KafkaListener(topics = "user_actions_avro", groupId = "kafka-demo-group")
    public void consumeMessage(UserAction userAction) {
        System.out.println("Received event: "+userAction);
    }
}
