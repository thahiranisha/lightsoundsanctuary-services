package com.lightsoundsanctuary.beacon_service.service.storage;

import java.io.IOException;

public interface StorageService {
    String saveText(String content, String folder, String fileName, String contentType) throws IOException;
}
