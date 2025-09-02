package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
    private final String topicName = "user-messages";
    private final KafkaTemplate<String, String> kafkaTemplate;
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
           log.info(String.format("Message sent -> %s", message));
           kafkaTemplate.send(topicName, message);
    }
}
