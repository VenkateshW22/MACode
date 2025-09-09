package com.example.dto.event;

import com.example.dto.enums.OrderStatus;
import com.example.dto.request.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private OrderRequestDto orderRequest;
    private OrderStatus orderStatus;
}
