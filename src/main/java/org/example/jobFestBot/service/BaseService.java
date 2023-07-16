package org.example.jobFestBot.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public interface BaseService<T> {
    ObjectMapper objectMapper = new ObjectMapper();
    T add(T t);
    List<T> getAllList();
    T getById(String id);
    void delete(String id);
    T update(T t);

}
