package com.example.order.controller;

import com.example.dto.request.OrderRequestDto;
import com.example.order.entity.PurchaseOrder;
import com.example.order.repository.OrderRepository;
import com.example.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping
    public PurchaseOrder createOrder(@Valid @RequestBody OrderRequestDto request){
        return orderService.createOrder(request);
    }

    @GetMapping
    public List<PurchaseOrder> getAllOrders(){
        return orderRepository.findAll();
    }
}
