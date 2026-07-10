package com.lightsoundsanctuary.user_service.controller;

import com.lightsoundsanctuary.user_service.dto.AuthRequest;
import com.lightsoundsanctuary.user_service.dto.AuthResponse;
import com.lightsoundsanctuary.user_service.dto.RegisterRequest;
import com.lightsoundsanctuary.user_service.entity.User;
import com.lightsoundsanctuary.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        String message = userService.register(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<AuthResponse> oauth2Success(@AuthenticationPrincipal OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        // Check if user already exists
        Optional<User> existingUser = userService.findByUsername(name);
        if (existingUser.isEmpty()) {
            userService.registerOAuthUser(name,email); // create new user
        }

        // Generate JWT for the Google user
        String token = userService.generateTokenForUser(name, email);
        return ResponseEntity.ok(new AuthResponse(token));
    }

}
