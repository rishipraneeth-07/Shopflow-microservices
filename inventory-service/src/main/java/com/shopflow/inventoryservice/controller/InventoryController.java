package com.shopflow.inventoryservice.controller;

import com.shopflow.inventoryservice.dto.CreateInventoryRequest;
import com.shopflow.inventoryservice.dto.InventoryResponse;
import com.shopflow.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public InventoryResponse createInventory(
            @Valid @RequestBody CreateInventoryRequest request) {

        return inventoryService.createInventory(request);
    }

    @GetMapping("/{productId}")
    public InventoryResponse getInventory(
            @PathVariable Long productId) {

        return inventoryService
                .getInventoryByProductId(productId);
    }

    @GetMapping("/{productId}/available")
    public boolean isProductAvailable(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {

        return inventoryService
                .isProductAvailable(productId, quantity);
    }
}