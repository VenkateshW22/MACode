package com.example.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDto {
    private Integer orderId;
    @NotNull(message = "User id is required")
    private Integer userId;
    @NotNull(message = "Product id is required")
    private Integer productId;
    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price should be greater than 0")
    private Double price;

}
