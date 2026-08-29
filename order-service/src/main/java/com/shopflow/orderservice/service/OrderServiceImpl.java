package com.shopflow.orderservice.service;

import com.shopflow.orderservice.client.UserClient;
import com.shopflow.orderservice.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl  implements OrderService {
    private final UserClient userClient;

    @Override
    public UserResponse getUserById(Long id) {
        return userClient.getUserById(id);
    }
}
