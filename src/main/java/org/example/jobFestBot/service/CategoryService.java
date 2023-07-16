package org.example.jobFestBot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.StringUtils;
import org.example.jobFestBot.model.Vacancy;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CategoryService implements BaseService<Vacancy> {
    private static final String path = "src/main/resources/category.json";
    @Override
    @SneakyThrows
    public Vacancy add(Vacancy category) {
        List<Vacancy> categoryList = getAllList();
        existCategory(category,categoryList);
        category.setId(UUID.randomUUID());
        categoryList.add(category);

        String valueAsString = objectMapper.writeValueAsString(category);
        FileUtils.write(path,valueAsString);
        return category;
    }

    private boolean existCategory(Vacancy category, List<Vacancy> categoryList) {
        return categoryList.stream().anyMatch(category1 -> StringUtils.equals(category1.getName(),category.getName()));
    }

    @SneakyThrows
    @Override
    public List<Vacancy> getAllList() {
        List<Vacancy> vacancies = objectMapper.readValue(Files.readString(Path.of(path)), new TypeReference<>() {
        });
        System.out.println(vacancies);
        return vacancies;
    }

    @Override
    public Vacancy getById(String id) {
        Optional<Vacancy> optionalCategory = getAllList().stream().filter(category -> StringUtils.equals(category.getId().toString(), id)).findFirst();
        if (optionalCategory.isPresent()){
            Vacancy category = optionalCategory.get();
            return category;
        }
        return null;
    }

    @SneakyThrows
    @Override
    public void delete(String  id) {
        List<Vacancy> allList = getAllList();
        Vacancy category1 = allList.stream().filter(category -> category.getId().equals(id)).findFirst().get();
        allList.remove(category1);

        objectMapper.writeValue(new File(path),allList);
    }

    @Override
    public Vacancy update(Vacancy category) {
        return null;
    }
}
