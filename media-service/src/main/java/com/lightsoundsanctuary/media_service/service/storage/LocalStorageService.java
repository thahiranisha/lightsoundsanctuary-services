package com.lightsoundsanctuary.media_service.service.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@ConditionalOnProperty(name = "storage.type", havingValue = "local", matchIfMissing = true)
public class LocalStorageService implements StorageService {

    @Value("${storage.local.upload-dir:uploads}")
    private String uploadDir;

    @Value("${storage.local.public-path:/uploads}")
    private String publicPath;

    @Override
    public String save(MultipartFile file, String folder) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required");
        }

        String originalFilename = StringUtils.cleanPath(
                file.getOriginalFilename() == null ? "file" : file.getOriginalFilename()
        );
        String storedFilename = UUID.randomUUID() + "-" + originalFilename;

        Path folderPath = Paths.get(uploadDir, folder).toAbsolutePath().normalize();
        Files.createDirectories(folderPath);

        Path targetPath = folderPath.resolve(storedFilename).normalize();
        if (!targetPath.startsWith(folderPath)) {
            throw new IOException("Invalid file path");
        }

        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

        return publicPath + "/" + folder + "/" + storedFilename;
    }
}
