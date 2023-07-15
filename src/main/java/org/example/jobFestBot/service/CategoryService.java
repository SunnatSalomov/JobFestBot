package org.example.jobFestBot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.jobFestBot.model.Category;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CategoryService implements BaseService<Category> {
    private static final String path = "src/main/resources/category.json";
    @Override
    @SneakyThrows
    public Category add(Category category) {
        List<Category> categoryList = getAllList();
        existCategory(category,categoryList);
        category.setId(UUID.randomUUID().toString());
        categoryList.add(category);

        String valueAsString = objectMapper.writeValueAsString(category);
        FileUtils.write(new File(path),valueAsString);
        return category;
    }

    private boolean existCategory(Category category, List<Category> categoryList) {
        return categoryList.stream().anyMatch(category1 -> StringUtils.equals(category1.getName(),category.getName()));
    }

    @SneakyThrows
    @Override
    public List<Category> getAllList() {
        return null;
    }

    @Override
    public Category getById(String id) {
        Optional<Category> optionalCategory = getAllList().stream().filter(category -> StringUtils.equals(category.getId(), id)).findFirst();
        if (optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            return category;
        }
        return null;
    }

    @SneakyThrows
    @Override
    public void delete(String  id) {
        List<Category> allList = getAllList();
        Category category1 = allList.stream().filter(category -> category.getId().equals(id)).findFirst().get();
        allList.remove(category1);

        objectMapper.writeValue(new File(path),allList);
    }
    @Override
    public Category update(Category category) {
        return null;
    }
}
