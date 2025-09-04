package com.example.dto.event;

import com.example.dto.enums.PaymentStatus;
import com.example.dto.request.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent {
    private OrderRequestDto orderRequest;
    private PaymentStatus paymentStatus;
}
