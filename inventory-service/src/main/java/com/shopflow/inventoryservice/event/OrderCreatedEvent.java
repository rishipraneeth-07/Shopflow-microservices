package com.shopflow.inventoryservice.event;

public record OrderCreatedEvent(
        Long orderId,
        Long userId
) {
}