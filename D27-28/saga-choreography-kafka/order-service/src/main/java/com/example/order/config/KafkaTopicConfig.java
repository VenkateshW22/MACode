package com.example.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name("order-topic").partitions(1).build();
    }
    @Bean
    public NewTopic paymentTopic() {
        return TopicBuilder.name("payment-topic").partitions(1).build();
    }
    @Bean
    public NewTopic shippingTopic() {
        return TopicBuilder.name("shipping-topic").partitions(1).build();
    }
}
