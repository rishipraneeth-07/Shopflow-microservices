package com.shopflow.orderservice.service;

import com.shopflow.orderservice.client.ProductClient;
import com.shopflow.orderservice.client.UserClient;
import com.shopflow.orderservice.dto.ProductResponse;
import com.shopflow.orderservice.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl  implements OrderService {
    private final UserClient userClient;
    private final ProductClient productClient;

    @Override
    public UserResponse getUserById(Long id) {
        return userClient.getUserById(id);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productClient.getProductById(id);
    }
}
