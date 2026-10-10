package com.shopflow.orderservice.event;

import java.util.List;

public record OrderCreatedEvent(
        Long orderId,
        Long userId,
        List<OrderCreatedItem> items
) {
}
