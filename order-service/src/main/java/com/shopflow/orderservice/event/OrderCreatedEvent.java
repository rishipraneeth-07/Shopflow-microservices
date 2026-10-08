package com.shopflow.orderservice.event;

public record OrderCreatedEvent(
        Long orderId,
        Long userId
) {
}
