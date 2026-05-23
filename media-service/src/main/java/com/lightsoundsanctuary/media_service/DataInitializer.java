package com.lightsoundsanctuary.media_service;

import com.lightsoundsanctuary.media_service.entity.Category;
import com.lightsoundsanctuary.media_service.repository.CategoryRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final CategoryRepository categoryRepository;

    public DataInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        seedCategory("Nature", "Natural environment sounds");
        seedCategory("Meditation", "Sounds for meditation and relaxation");
        seedCategory("Rain", "Rain and water sounds");
        seedCategory("Forest", "Forest and wildlife sounds");
        seedCategory("Ocean", "Ocean and wave sounds");
        seedCategory("Binaural", "Binaural beats and frequencies");
    }

    private void seedCategory(String name, String description) {
        if (categoryRepository.findByNameIgnoreCase(name).isEmpty()) {
            Category category = new Category();
            category.setName(name);
            category.setDescription(description);
            categoryRepository.save(category);
        }
    }
}