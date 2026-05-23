package com.lightsoundsanctuary.beacon_service.service.storage;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@ConditionalOnProperty(name = "storage.type", havingValue = "s3")
public class S3StorageService implements StorageService {

    @Override
    public String saveText(String content, String folder,
                           String fileName, String contentType) throws IOException {
        // S3 implementation removed - AWS free tier expired
        // To re-enable: add AWS SDK dependency and implement upload logic
        throw new UnsupportedOperationException("S3 storage not configured");
    }
}