package com.lightsoundsanctuary.media_service.repository;

import com.lightsoundsanctuary.media_service.entity.Sound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoundRepository extends JpaRepository<Sound, Long> {

}
