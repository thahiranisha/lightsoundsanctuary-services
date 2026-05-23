package com.lightsoundsanctuary.notification_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private static final Logger log =
            Logger.getLogger(NotificationController.class.getName());

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> send(
            @RequestBody Map<String, String> request) {

        String to = request.getOrDefault("to", "unknown");
        String message = request.getOrDefault("message", "");

        log.info("NOTIFICATION → to: " + to + " | message: " + message
                + " | time: " + LocalDateTime.now());

        return ResponseEntity.ok(Map.of(
                "status", "sent",
                "to", to,
                "message", message
        ));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "notification-service running"));
    }
}