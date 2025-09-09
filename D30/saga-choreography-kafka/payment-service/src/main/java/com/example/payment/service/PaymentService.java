package com.example.payment.service;

import com.example.dto.enums.OrderStatus;
import com.example.dto.enums.PaymentStatus;
import com.example.dto.enums.ShippingStatus;
import com.example.dto.event.OrderEvent;
import com.example.dto.event.PaymentEvent;
import com.example.dto.event.ShippingEvent;
import com.example.dto.request.OrderRequestDto;
import com.example.payment.entity.Payment;
import com.example.payment.entity.UserBalance;
import com.example.payment.repository.PaymentRepository;
import com.example.payment.repository.UserBalanceRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
// Add the KafkaListener annotation at the class level
@KafkaListener(topics = {"${app.kafka.topic.payment}", "${app.kafka.topic.shipping}"}, groupId = "payment-group")
public class PaymentService {
    @Autowired private UserBalanceRepository balanceRepo;
    @Autowired private PaymentRepository paymentRepo;
    @Autowired private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topic.shipping}") private String shippingTopic;
    @Value("${app.kafka.topic.order}") private String orderTopic;

    @PostConstruct
    public void initUserBalance() {
        balanceRepo.saveAll(List.of(
                new UserBalance(null, 101, 5000.0),
                new UserBalance(null, 102, 300.0)
        ));
    }

    // This handler will be invoked for OrderEvent messages
    @KafkaHandler
    @Transactional
    public void processPayment(OrderEvent orderEvent) {
        log.info("Received order event to process payment: {}", orderEvent.getOrderRequest().getOrderId());
        OrderRequestDto orderRequest = orderEvent.getOrderRequest();
        Payment payment = new Payment();
        payment.setOrderId(orderRequest.getOrderId());

        UserBalance userBalance = balanceRepo.findByUserId(orderRequest.getUserId()).orElseThrow();

        if (userBalance.getBalance() >= orderRequest.getPrice()) {
            userBalance.setBalance(userBalance.getBalance() - orderRequest.getPrice());
            balanceRepo.save(userBalance);
            payment.setPaymentStatus(PaymentStatus.PAYMENT_COMPLETED);
            paymentRepo.save(payment);

            PaymentEvent paymentEvent = new PaymentEvent(orderRequest, PaymentStatus.PAYMENT_COMPLETED);
            kafkaTemplate.send(shippingTopic, paymentEvent);
            log.info("Payment successful. Sent event to topic: {}", shippingTopic);
        } else {
            payment.setPaymentStatus(PaymentStatus.PAYMENT_FAILED);
            paymentRepo.save(payment);
            OrderEvent reverseEvent = new OrderEvent(orderRequest, OrderStatus.ORDER_CANCELLED);
            kafkaTemplate.send(orderTopic, reverseEvent);
            log.info("Payment failed. Sent reverse event to topic: {}", orderTopic);
        }
    }

    // This handler will be invoked for ShippingEvent messages
    @KafkaHandler
    @Transactional
    public void handleShippingEvent(ShippingEvent shippingEvent) {
        if (ShippingStatus.SHIPPING_FAILED.equals(shippingEvent.getShippingStatus())) {
            log.info("Received shipping failed event, initiating refund for order: {}", shippingEvent.getOrderRequest().getOrderId());
            OrderRequestDto orderRequest = shippingEvent.getOrderRequest();

            // Compensating Transaction: Refund
            UserBalance userBalance = balanceRepo.findByUserId(orderRequest.getUserId()).orElseThrow();
            userBalance.setBalance(userBalance.getBalance() + orderRequest.getPrice());
            balanceRepo.save(userBalance);

            paymentRepo.findByOrderId(orderRequest.getOrderId()).ifPresent(p -> {
                p.setPaymentStatus(PaymentStatus.PAYMENT_REFUNDED);
                paymentRepo.save(p);
            });

            OrderEvent reverseEvent = new OrderEvent(orderRequest, OrderStatus.ORDER_CANCELLED);
            kafkaTemplate.send(orderTopic, reverseEvent);
            log.info("Refund successful. Sent cancellation event to topic: {}", orderTopic);
        }
    }

    // This handler will ignore any PaymentEvent messages consumed from the shipping topic
    @KafkaHandler
    public void handlePaymentEvent(PaymentEvent paymentEvent) {
        log.info("PaymentEvent received and ignored by PaymentService consumer: {}", paymentEvent.getOrderRequest().getOrderId());
    }
}