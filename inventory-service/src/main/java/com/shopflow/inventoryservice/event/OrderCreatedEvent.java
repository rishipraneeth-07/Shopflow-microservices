package com.shopflow.inventoryservice.event;

import java.util.List;

public record OrderCreatedEvent(
        Long orderId,
        Long userId,
        List<OrderCreatedItem> items
) {
}