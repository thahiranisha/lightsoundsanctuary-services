package com.lightsoundsanctuary.media_service.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    String save(MultipartFile file, String folder) throws IOException;
}
