package com.shopflow.orderservice.event;

public record OrderCreatedItem(
        Long productId,
        Integer quantity
) {
}