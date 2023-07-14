package org.example.jobFestBot.service;

import org.example.jobFestBot.model.Category;
import org.example.jobFestBot.model.Database;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CategoryService implements BaseService<Category> {
    private static List<Category> categories = Database.CATEGORIES;
    @Override
    public Category add(Category category) {
        Optional<Category> first = categories.stream()
                .filter(category1 -> category1.getName().equals(category.getName()))
                .findFirst();
        if (first.isPresent()) {
            throw new RuntimeException("Category already exists");
        }

        categories.add(category);
        return category;
    }

    @Override
    public List<Category> getAllList() {
        return categories;
    }

    @Override
    public Category getById(UUID id) {
        Optional<Category> categoryOptional = categories.stream()
                .parallel().filter(category -> Objects.equals(category.getId(), id))
                .findFirst();
        return categoryOptional.orElse(null);
    }

    @Override
    public void delete(UUID id) {
        String ids = String.valueOf(id);

    }

    @Override
    public Category update(Category category) {
        return null;
    }
}
