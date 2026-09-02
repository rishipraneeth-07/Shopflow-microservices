package com.shopflow.orderservice.service;

import com.shopflow.orderservice.dto.CreateOrderRequest;
import com.shopflow.orderservice.dto.OrderResponse;
import com.shopflow.orderservice.dto.ProductResponse;
import com.shopflow.orderservice.dto.UserResponse;

import java.util.List;

public interface OrderService {
    UserResponse getUserById(Long id);
    ProductResponse getProductById(Long id);
    Boolean isProductAvailable(Long productId,Integer quantity);

    OrderResponse createOrder(CreateOrderRequest request);
    OrderResponse getOrderById(Long id);
    List<OrderResponse> getOrdersByUserId(Long userId);
}
