package com.lightsoundsanctuary.user_service;

import com.lightsoundsanctuary.user_service.entity.UserRole;
import com.lightsoundsanctuary.user_service.repository.UserRoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final UserRoleRepository userRoleRepository;

    public DataInitializer(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRoleRepository.findByName("USER").isEmpty()) {
            UserRole userRole = new UserRole();
            userRole.setName("USER");
            userRoleRepository.save(userRole);
        }

        if (userRoleRepository.findByName("ADMIN").isEmpty()) {
            UserRole adminRole = new UserRole();
            adminRole.setName("ADMIN");
            userRoleRepository.save(adminRole);
        }
    }
}