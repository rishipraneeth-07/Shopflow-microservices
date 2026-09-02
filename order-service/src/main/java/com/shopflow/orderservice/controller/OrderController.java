package com.shopflow.orderservice.controller;

import com.shopflow.orderservice.dto.CreateOrderRequest;
import com.shopflow.orderservice.dto.OrderResponse;
import com.shopflow.orderservice.dto.ProductResponse;
import com.shopflow.orderservice.dto.UserResponse;
import com.shopflow.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/test-user/{id}")
    UserResponse getUserById(@PathVariable Long id){
        return orderService.getUserById(id);
    }

    @GetMapping("/test-product/{id}")
    ProductResponse getProductById(@PathVariable Long id){
        return orderService.getProductById(id);
    }

    @GetMapping("/test-inventory/{productId}/available")
    Boolean isInventoryAvailable(@PathVariable Long productId, @RequestParam Integer quantity){
        return orderService.isProductAvailable(productId, quantity);
    }

    @PostMapping
    OrderResponse createOrder(@RequestBody @Valid CreateOrderRequest request){
        return orderService.createOrder(request);
    }

}
