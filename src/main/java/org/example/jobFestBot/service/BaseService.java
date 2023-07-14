package org.example.jobFestBot.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

public interface BaseService<T> {
    ObjectMapper objectMapper = new ObjectMapper();
    T add(T t);
    List<T> getAllList();
    T getById(UUID id);
    void delete(UUID id);
    T update(T t);

}
