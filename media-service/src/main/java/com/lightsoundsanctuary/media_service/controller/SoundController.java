package com.lightsoundsanctuary.media_service.controller;

import com.lightsoundsanctuary.media_service.entity.Sound;
import com.lightsoundsanctuary.media_service.service.SoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class SoundController {

    private final SoundService soundService;

    @GetMapping("/sounds")
    public ResponseEntity<List<Sound>> getAllSounds() {
        return ResponseEntity.ok(soundService.getAllSounds());
    }

    @PostMapping("/upload")
    public ResponseEntity<Sound> uploadSound(
            @RequestParam("title") String title,
            @RequestParam("category") String category,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Sound sound = soundService.uploadSound(title, category, file);
        return ResponseEntity.ok(sound);
    }
}
