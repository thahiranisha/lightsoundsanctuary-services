package com.lightsoundsanctuary.media_service.service.impl;

import com.lightsoundsanctuary.media_service.entity.Category;
import com.lightsoundsanctuary.media_service.entity.Sound;
import com.lightsoundsanctuary.media_service.repository.CategoryRepository;
import com.lightsoundsanctuary.media_service.repository.SoundRepository;
import com.lightsoundsanctuary.media_service.service.SoundService;
import com.lightsoundsanctuary.media_service.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SoundServiceImpl implements SoundService {

    private final SoundRepository soundRepository;
    private final CategoryRepository categoryRepository;
    private final StorageService storageService;

    @Override
    public List<Sound> getAllSounds() {
        return soundRepository.findAll();
    }

    @Override
    public Sound uploadSound(String title, String categoryName, MultipartFile file) throws IOException {
        String fileUrl = storageService.save(file, "sounds");

        Category category = categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryName));

        Sound sound = new Sound(null, title, fileUrl, category);
        return soundRepository.save(sound);
    }
}
