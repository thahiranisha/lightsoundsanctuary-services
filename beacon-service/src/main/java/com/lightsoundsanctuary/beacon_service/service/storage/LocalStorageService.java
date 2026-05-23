package com.lightsoundsanctuary.beacon_service.service.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@ConditionalOnProperty(name = "storage.type", havingValue = "local", matchIfMissing = true)
public class LocalStorageService implements StorageService {

    @Value("${storage.local.upload-dir:uploads}")
    private String uploadDir;

    @Value("${storage.local.public-path:/uploads}")
    private String publicPath;

    @Override
    public String saveText(String content, String folder, String fileName, String contentType) throws IOException {
        String safeFileName = StringUtils.cleanPath(fileName == null ? "file.txt" : fileName);

        Path folderPath = Paths.get(uploadDir, folder).toAbsolutePath().normalize();
        Files.createDirectories(folderPath);

        Path targetPath = folderPath.resolve(safeFileName).normalize();
        if (!targetPath.startsWith(folderPath)) {
            throw new IOException("Invalid file path");
        }

        Files.writeString(targetPath, content == null ? "" : content, StandardCharsets.UTF_8);

        return publicPath + "/" + folder + "/" + safeFileName;
    }
}
