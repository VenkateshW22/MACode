package com.example.shipping.service;

import com.example.dto.event.PaymentEvent;
import com.example.shipping.repository.ShipmentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShippingService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topic.shipping}")
    private String shippingTopic;

    @Value("${app.kafka.topic.order}")
    private String orderTopic;


    @KafkaListener(topics = "${app.kafka.topic.shipping}", groupId = "shipping-group")
    @Transactional
    public void arrangeShipping(PaymentEvent paymentEvent) {}

}
