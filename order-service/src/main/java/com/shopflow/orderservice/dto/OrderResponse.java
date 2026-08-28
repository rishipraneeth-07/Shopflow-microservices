package com.shopflow.orderservice.dto;

import com.shopflow.orderservice.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(

        Long id,
        Long userId,
        OrderStatus status,
        BigDecimal totalAmount,
        List<OrderItemResponse> items

) {
}