package com.lightsoundsanctuary.user_service.repository;

import com.lightsoundsanctuary.user_service.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    Optional<UserRole> findByName(String name);

}
