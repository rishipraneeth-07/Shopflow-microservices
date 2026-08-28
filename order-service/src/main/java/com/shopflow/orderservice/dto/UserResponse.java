package com.shopflow.orderservice.dto;

public record UserResponse(
        Long id,
        String name,
        String email
) {
}
