package com.example.order.service;

import com.example.dto.enums.OrderStatus;
import com.example.dto.event.OrderEvent;
import com.example.dto.request.OrderRequestDto;
import com.example.order.entity.PurchaseOrder;
import com.example.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${app.kafka.topic.payment}")
    private String paymentTopic;

    @Transactional
    public PurchaseOrder createOrder(OrderRequestDto orderRequestDto){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setProductId(orderRequestDto.getProductId());
        purchaseOrder.setPrice(orderRequestDto.getPrice());
        purchaseOrder.setUserId(orderRequestDto.getUserId());
        purchaseOrder.setStatus(OrderStatus.ORDER_CREATED);

        purchaseOrder = orderRepository.save(purchaseOrder);

        orderRequestDto.setOrderId(purchaseOrder.getId());

        OrderEvent orderEvent = new OrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);
        kafkaTemplate.send(paymentTopic, orderEvent);
        log.info("OrderEvent: "+ orderEvent);
        log.info("1. Order created successfully with order id: "+ purchaseOrder.getId());
        log.info("2. Payment event sent to kafka topic: "+ paymentTopic);
        return purchaseOrder;
    }

    @KafkaListener(topics = "${app.kafka.topic.order}", groupId = "order-group")
    public void updateOrderStatus(OrderEvent orderEvent){
        log.info("1. Received Order status updated successfully with order id: "+ orderEvent.getOrderRequest().getOrderId());
        log.info("2. Current Order status: "+ orderEvent.getOrderStatus());
        orderRepository.findById(orderEvent.getOrderRequest().getOrderId()).ifPresent(order -> {
            order.setStatus(orderEvent.getOrderStatus());
            orderRepository.save(order);
            log.info("3. Order status updated to "+orderEvent.getOrderStatus() +" successfully with order id: "+ orderEvent.getOrderRequest().getOrderId());
        });

    }
}
