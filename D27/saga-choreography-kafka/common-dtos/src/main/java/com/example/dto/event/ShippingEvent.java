package com.example.dto.event;

import com.example.dto.enums.ShippingStatus;
import com.example.dto.request.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingEvent {
    private OrderRequestDto orderRequest;
    private ShippingStatus shippingStatus;
}
