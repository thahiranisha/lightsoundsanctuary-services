package com.lightsoundsanctuary.media_service.service;

import com.lightsoundsanctuary.media_service.entity.Sound;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SoundService {
    List<Sound> getAllSounds();
    Sound uploadSound(String title, String category, MultipartFile file) throws IOException;
}
