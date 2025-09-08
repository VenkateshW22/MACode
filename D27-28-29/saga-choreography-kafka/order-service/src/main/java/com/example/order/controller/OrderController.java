package com.example.order.controller;

import com.example.dto.request.OrderRequestDto;
import com.example.order.entity.PurchaseOrder;
import com.example.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public PurchaseOrder createOrder(@Valid @RequestBody OrderRequestDto request){
        return orderService.createOrder(request);
    }
}
