package org.example.jobFestBot.service;

import lombok.Data;

import java.util.UUID;
@Data
public interface BaseService {
    String id = UUID.randomUUID().toString();
}
