package org.example.jobFestBot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.StringUtils;
import org.example.jobFestBot.model.Vacancy;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CategoryService implements BaseService<Vacancy> {
    private static final String path = "src/main/resources/category.json";
    @Override
    @SneakyThrows
    public void add(Vacancy category) {
        List<Vacancy> categoryList = getAllList();
        existCategory(category,categoryList);
        category.setId(UUID.randomUUID());
        categoryList.add(category);

        String valueAsString = objectMapper.writeValueAsString(category);
        FileUtils.write(path,valueAsString);
    }

    private boolean existCategory(Vacancy category, List<Vacancy> categoryList) {
        return categoryList.stream().anyMatch(category1 -> StringUtils.equals(category1.getName(),category.getName()));
    }

    @SneakyThrows
    @Override
    public List<Vacancy> getAllList() {
        List<Vacancy> vacancies = objectMapper.readValue(Files.readString(Path.of(path)), new TypeReference<>() {
        });
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

    public Vacancy getVacancyById(String id){
        System.out.println("id = " + id);
            String id0= "" ;
        if (!id.equals(".")){
            id0 =id0 + id.substring(id.length()-36);
        }
        String id1 = id0;
        System.out.println("id1 = " + id1);
        return getAllList().stream().filter(vacancy -> Objects.equals(vacancy.getId().toString(),id1)).findFirst().get();
    }
    public Vacancy getVacancyById1(String id){
       return getAllList().stream().filter(vacancy -> Objects.equals(vacancy.getParentId(),id)).findFirst().orElse(null);
    }

    public List<Vacancy> getVacancyByParentId(String id){
        return getAllList().stream().filter(vacancy -> Objects.equals(vacancy.getParentId(),id)).toList();
    }
}
