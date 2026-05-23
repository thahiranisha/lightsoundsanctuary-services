package com.lightsoundsanctuary.beacon_service.controllers;

import com.lightsoundsanctuary.beacon_service.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/beacons")
@RequiredArgsConstructor
public class BeaconController {

    private final StorageService storageService;

    @PostMapping("/upload")
    public String uploadBeacon(@RequestBody String jsonData) throws IOException {
        String fileName = "beacon-" + UUID.randomUUID() + ".json";
        String fileUrl = storageService.saveText(jsonData, "beacons", fileName, "application/json");
        return "Uploaded beacon data: " + fileUrl;
    }
}
