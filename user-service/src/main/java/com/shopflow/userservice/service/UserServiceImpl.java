package com.shopflow.userservice.service;

import com.shopflow.userservice.dto.CreateUserRequest;
import com.shopflow.userservice.dto.LoginRequest;
import com.shopflow.userservice.dto.LoginResponse;
import com.shopflow.userservice.dto.UserResponse;
import com.shopflow.userservice.entity.User;
import com.shopflow.userservice.repository.UserRepository;
import com.shopflow.userservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Override
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setName(createUserRequest.name());
        user.setEmail(createUserRequest.email());
        user.setPassword(passwordEncoder.encode(createUserRequest.password()));
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

    @Override
    public LoginResponse login(LoginRequest request) {
        User user= userRepository.findByEmail(request.email()).orElseThrow(
                ()->new RuntimeException("User with email " + request.email() + " not found")
        );
        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        String token = jwtService.generateToken(user.getId(),user.getRole().name());
        return new LoginResponse(token);
    }
}
