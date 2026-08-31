package com.shopflow.orderservice.service;

import com.shopflow.orderservice.dto.ProductResponse;
import com.shopflow.orderservice.dto.UserResponse;

public interface OrderService {
    UserResponse getUserById(Long id);
    ProductResponse getProductById(Long id);
    Boolean isProductAvailable(Long productId,Integer quantity);
}
