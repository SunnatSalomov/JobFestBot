package org.example.jobFestBot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.jobFestBot.model.Category;

import java.util.List;

public interface BaseService<T> {
    ObjectMapper objectMapper = new ObjectMapper();
    T add(T t);
    List<T> getAllList();
    T getById(String id);

    Category getById(String id);

    void delete(String id);
    T update(T t);

}
