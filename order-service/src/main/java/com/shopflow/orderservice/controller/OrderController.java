package com.shopflow.orderservice.controller;

import com.shopflow.orderservice.dto.UserResponse;
import com.shopflow.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/test-user/{id}")
    UserResponse getUserById(@PathVariable Long id){
        return orderService.getUserById(id);
    }

}
