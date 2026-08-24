package com.shopflow.inventoryservice.service;

import com.shopflow.inventoryservice.dto.CreateInventoryRequest;
import com.shopflow.inventoryservice.dto.InventoryResponse;
import com.shopflow.inventoryservice.entity.Inventory;
import com.shopflow.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public InventoryResponse createInventory(CreateInventoryRequest request) {

        Inventory inventory = new Inventory();

        inventory.setProductId(request.productId());
        inventory.setQuantity(request.quantity());

        Inventory savedInventory = inventoryRepository.save(inventory);

        return mapToResponse(savedInventory);
    }

    @Override
    public InventoryResponse getInventoryByProductId(Long productId) {

        Inventory inventory = inventoryRepository
                .findByProductId(productId)
                .orElseThrow(() ->
                        new RuntimeException("Inventory not found"));

        return mapToResponse(inventory);
    }

    @Override
    public boolean isProductAvailable(
            Long productId,
            Integer quantity) {

        Inventory inventory = inventoryRepository
                .findByProductId(productId)
                .orElseThrow(() ->
                        new RuntimeException("Inventory not found"));

        return inventory.getQuantity() >= quantity;
    }

    private InventoryResponse mapToResponse(Inventory inventory) {

        return new InventoryResponse(
                inventory.getId(),
                inventory.getProductId(),
                inventory.getQuantity()
        );
    }
}