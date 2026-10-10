package com.shopflow.inventoryservice.event;

public record OrderCreatedItem(
        Long productId,
        Integer quantity
) {
}