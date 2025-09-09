package com.example.shipping.service;

import com.example.dto.enums.OrderStatus;
import com.example.dto.enums.PaymentStatus;
import com.example.dto.enums.ShippingStatus;
import com.example.dto.event.OrderEvent;
import com.example.dto.event.PaymentEvent;
import com.example.dto.event.ShippingEvent;
import com.example.dto.request.OrderRequestDto;
import com.example.shipping.entity.Shipment;
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
    public void arrangeShipping(PaymentEvent paymentEvent) {
        if (PaymentStatus.PAYMENT_COMPLETED.equals(paymentEvent.getPaymentStatus())) {
            log.info("Received payment completed event for order: {}", paymentEvent.getOrderRequest().getOrderId());
            OrderRequestDto orderRequest = paymentEvent.getOrderRequest();
            Shipment shipment = new Shipment();
            shipment.setOrderId(orderRequest.getOrderId());

            // Simulating failure for a specific product ID to test compensation transaction
            if (orderRequest.getProductId() == 999) {
                shipment.setStatus(ShippingStatus.SHIPPING_FAILED);
                shipmentRepository.save(shipment);

                ShippingEvent shippingEvent = new ShippingEvent(orderRequest, ShippingStatus.SHIPPING_FAILED);
                kafkaTemplate.send(shippingTopic, shippingEvent);
                kafkaTemplate.send(orderTopic, shippingEvent);
                log.error("Shipping failed. Sent compensation event to topic: {}", shippingTopic);
            } else {
                shipment.setStatus(ShippingStatus.SHIPPING_SCHEDULED);
                shipmentRepository.save(shipment);

                OrderEvent orderEvent = new OrderEvent(orderRequest, OrderStatus.ORDER_COMPLETED);
                kafkaTemplate.send(orderTopic, orderEvent);
                log.info("Shipping scheduled. Sent final event to topic: {}", orderTopic);
            }
        }
    }

}
