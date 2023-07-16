package org.example.jobFestBot.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public interface BaseService<T> {
    ObjectMapper objectMapper = new ObjectMapper();
    T add(T t);
    List<T> getAllList() throws IOException;
    T getById(String id);
    void delete(String id);
    T update(T t);

}
