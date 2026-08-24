package com.shopflow.inventoryservice.dto;

public record InventoryResponse(
        Long id,
        Long productId,
        Integer quantity
) {
}