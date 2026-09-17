package com.shopflow.userservice.service;

import com.shopflow.userservice.dto.CreateUserRequest;
import com.shopflow.userservice.dto.LoginRequest;
import com.shopflow.userservice.dto.LoginResponse;
import com.shopflow.userservice.dto.UserResponse;

public interface UserService {
    UserResponse  createUser(CreateUserRequest createUserRequest);
    UserResponse getUserById(Long id);
    LoginResponse login(LoginRequest request);

}
