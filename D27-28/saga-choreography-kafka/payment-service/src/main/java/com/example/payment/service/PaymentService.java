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
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topic.order")
    private String orderTopic;

    @Value("${app.kafka.topic.shipping}")
    private String shippingTopic;

    @PostConstruct
    public void init() {
        userBalanceRepository.saveAll(List.of(
                new UserBalance(null, 101, 5000.0),
                new UserBalance(null, 102, 300.0)
        ));
    }

    @KafkaListener(topics = "${app.kafka.topic.payment}", groupId = "payment-group")
    @Transactional
    public void processPayment(OrderEvent orderEvent) {
        log.info("Processing payment for order: {}", orderEvent.getOrderRequest().getOrderId());
        OrderRequestDto orderRequestDto = orderEvent.getOrderRequest();
        Payment payment = new Payment();
        payment.setOrderId(orderRequestDto.getOrderId());

        UserBalance userBalance = userBalanceRepository.findByUserId(orderRequestDto.getUserId()).orElseThrow();

        if (userBalance.getBalance() >= orderRequestDto.getPrice()){
            userBalance.setBalance(userBalance.getBalance()-orderRequestDto.getPrice());
            userBalanceRepository.save(userBalance);
            payment.setPaymentStatus(PaymentStatus.PAYMENT_COMPLETED);
            paymentRepository.save(payment);

            PaymentEvent paymentEvent = new PaymentEvent(orderRequestDto, PaymentStatus.PAYMENT_COMPLETED);

            kafkaTemplate.send(orderTopic, paymentEvent);
            log.info("Payment processed successfully for order: {}", orderRequestDto.getOrderId());
            log.info("Payment event sent to topic: {}", orderTopic);
        }else {
            payment.setPaymentStatus(PaymentStatus.PAYMENT_FAILED);
            paymentRepository.save(payment);
            OrderEvent reverseEvent = new OrderEvent(orderRequestDto, OrderStatus.ORDER_CANCELLED);
            kafkaTemplate.send(orderTopic, reverseEvent);
            log.info("Payment failed for order: {} with payment status: {} and order status: {} in topic: {}", orderRequestDto.getOrderId(), payment.getPaymentStatus(), reverseEvent.getOrderStatus(), orderTopic);

        }
    }

    @KafkaListener(topics = "${app.kafka.topic.shipping}" , groupId = "shipping-group")
    @Transactional
    public void handleShippingEvent(ShippingEvent shippingEvent) {
        log.info("Processing shipping for order: {}", shippingEvent.getOrderRequest().getOrderId());
        if (ShippingStatus.SHIPPING_FAILED.equals(shippingEvent.getShippingStatus())){
            log.info("Shipping failed for order: {} with shipping status: {} in topic: {}", shippingEvent.getOrderRequest().getOrderId(), shippingEvent.getShippingStatus(), shippingTopic);
            OrderRequestDto orderRequestDto = shippingEvent.getOrderRequest();

            UserBalance userBalance = userBalanceRepository.findByUserId(orderRequestDto.getUserId()).orElseThrow();
            userBalance.setBalance(userBalance.getBalance()+orderRequestDto.getPrice());
            userBalanceRepository.save(userBalance);

            paymentRepository.findByOrderId(orderRequestDto.getOrderId()).ifPresent(payment -> {
                payment.setPaymentStatus(PaymentStatus.PAYMENT_REFUNDED);
                paymentRepository.save(payment);
            });

            OrderEvent reverseEvent = new OrderEvent(orderRequestDto, OrderStatus.ORDER_CANCELLED);
            kafkaTemplate.send(orderTopic, reverseEvent);
            log.info("Payment refunded for order: {} and order status: {} in topic: {}", orderRequestDto.getOrderId(), reverseEvent.getOrderStatus(), orderTopic);
        }
    }

}

