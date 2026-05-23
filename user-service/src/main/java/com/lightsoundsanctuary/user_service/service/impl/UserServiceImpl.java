package com.lightsoundsanctuary.user_service.service.impl;

import com.lightsoundsanctuary.user_service.dto.AuthRequest;
import com.lightsoundsanctuary.user_service.dto.AuthResponse;
import com.lightsoundsanctuary.user_service.dto.RegisterRequest;
import com.lightsoundsanctuary.user_service.entity.User;
import com.lightsoundsanctuary.user_service.entity.UserRole;
import com.lightsoundsanctuary.user_service.repository.UserRepository;
import com.lightsoundsanctuary.user_service.repository.UserRoleRepository;
import com.lightsoundsanctuary.user_service.security.JwtUtil;
import com.lightsoundsanctuary.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(
            PasswordEncoder passwordEncoder,
            @Lazy AuthenticationManager authenticationManager
    ) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String register(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByUsername(request.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists.");
        }

        UserRole role = userRoleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Default role USER not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        userRepository.save(user);
        return "User registered successfully.";
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String userRole = user.getRole().getName();  // Get role name directly
        String token = jwtUtil.generateToken(user.getUsername(), userRole);
        return new AuthResponse(token);
    }


    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void registerOAuthUser(String name,String email) {
        if (userRepository.findByUsername(name).isPresent()) {
            return; // user already exists
        }

        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setProvider("google");

        // ✅ Fetch default role (e.g., "USER")
        UserRole role = userRoleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Default role USER not found in DB"));

        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public String generateTokenForUser(String name, String email) {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String userRole = user.getRole().getName();  // Use the role name
        return jwtUtil.generateToken(user.getUsername(), userRole);
    }


}
