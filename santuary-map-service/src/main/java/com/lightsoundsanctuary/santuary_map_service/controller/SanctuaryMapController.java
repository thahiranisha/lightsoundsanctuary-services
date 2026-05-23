package com.lightsoundsanctuary.santuary_map_service.controller;

import com.lightsoundsanctuary.santuary_map_service.entity.Sanctuary;
import com.lightsoundsanctuary.santuary_map_service.repository.SanctuaryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/map")
public class SanctuaryMapController {

    private final SanctuaryRepository sanctuaryRepository;

    public SanctuaryMapController(SanctuaryRepository sanctuaryRepository) {
        this.sanctuaryRepository = sanctuaryRepository;
    }

    @GetMapping("/sanctuaries")
    public ResponseEntity<List<Sanctuary>> getAllSanctuaries() {
        return ResponseEntity.ok(sanctuaryRepository.findAll());
    }

    @GetMapping("/sanctuaries/type/{type}")
    public ResponseEntity<List<Sanctuary>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(sanctuaryRepository.findByType(type));
    }

    @PostMapping("/sanctuaries")
    public ResponseEntity<Sanctuary> addSanctuary(@RequestBody Sanctuary sanctuary) {
        return ResponseEntity.ok(sanctuaryRepository.save(sanctuary));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "sanctuary-map-service running"));
    }
}