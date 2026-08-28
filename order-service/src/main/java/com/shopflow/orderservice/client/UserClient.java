package com.shopflow.orderservice.client;

import com.shopflow.orderservice.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service",url = "http://localhost:8081")
public interface UserClient {

    @GetMapping("/users/{id}")
    UserResponse getUserById(@PathVariable Long id);
}
