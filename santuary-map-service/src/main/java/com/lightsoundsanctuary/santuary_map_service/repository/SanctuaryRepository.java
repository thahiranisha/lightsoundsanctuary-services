package com.lightsoundsanctuary.santuary_map_service.repository;

import com.lightsoundsanctuary.santuary_map_service.entity.Sanctuary;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SanctuaryRepository extends JpaRepository<Sanctuary, Long> {
    List<Sanctuary> findByType(String type);
}