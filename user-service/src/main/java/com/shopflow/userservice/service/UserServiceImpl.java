package com.shopflow.userservice.service;

import com.shopflow.userservice.dto.CreateUserRequest;
import com.shopflow.userservice.dto.UserResponse;
import com.shopflow.userservice.entity.User;
import com.shopflow.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setName(createUserRequest.name());
        user.setEmail(createUserRequest.email());
        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                ()->new RuntimeException("User with id " + id + " not found")
        );
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
