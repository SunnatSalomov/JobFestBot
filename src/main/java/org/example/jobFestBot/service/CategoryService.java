package org.example.jobFestBot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.jobFestBot.model.Category;

import java.util.List;
import java.util.UUID;

public class CategoryService implements BaseService<Category> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Override
    public Category add(Category category) {
        return null;
    }

    @Override
    public List<Category> getAllList() {
        return null;
    }

    @Override
    public Category getById(String id) {
       return null;
    }

    @Override
    public void delete(String  id) {

    }
    @Override
    public Category update(Category category) {
        return null;
    }
}
