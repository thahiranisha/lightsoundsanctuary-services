package com.lightsoundsanctuary.user_service.service;

import com.lightsoundsanctuary.user_service.dto.AuthRequest;
import com.lightsoundsanctuary.user_service.dto.AuthResponse;
import com.lightsoundsanctuary.user_service.dto.RegisterRequest;
import com.lightsoundsanctuary.user_service.entity.User;

import java.util.Optional;

public interface UserService {
    String register(RegisterRequest request);
    AuthResponse login(AuthRequest request);
    Optional<User> findByUsername(String username);
    void registerOAuthUser(String name, String email);
    String generateTokenForUser(String name, String email);

}
