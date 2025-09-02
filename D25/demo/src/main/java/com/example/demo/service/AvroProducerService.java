package com.example.demo.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class AvroProducerService {
    private final KafkaTemplate<String, UserAction> kafkaTemplate;

    public AvroProducerService(KafkaTemplate<String, UserAction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserAction() {
        UserAction action = UserAction.newBuilder()
                .setUserId("123")
                .setAction("clicked-profile")
                .setTimestamp(Instant.now().toEpochMilli())
                .build();

        kafkaTemplate.send("user_actions_avro", action.getUserId(), action);
        System.out.println("Sent Avro message: " + action);
    }
}