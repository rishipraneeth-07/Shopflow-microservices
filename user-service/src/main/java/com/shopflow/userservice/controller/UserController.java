package com.shopflow.userservice.controller;

import com.shopflow.userservice.dto.CreateUserRequest;
import com.shopflow.userservice.dto.UserResponse;
import com.shopflow.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse createUser(
            @Valid @RequestBody CreateUserRequest request) {

        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(
            @PathVariable Long id) {

        return userService.getUserById(id);
    }
}