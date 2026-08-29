package com.shopflow.orderservice.service;

import com.shopflow.orderservice.dto.UserResponse;

public interface OrderService {
    UserResponse getUserById(Long id);
}
