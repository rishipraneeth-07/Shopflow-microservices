package com.shopflow.inventoryservice.service;

import com.shopflow.inventoryservice.dto.CreateInventoryRequest;
import com.shopflow.inventoryservice.dto.InventoryResponse;

public interface InventoryService {
    InventoryResponse createInventory(CreateInventoryRequest request);
    InventoryResponse getInventoryByProductId(Long productId);
    boolean isProductAvailable(Long productId, Integer quantity);
}