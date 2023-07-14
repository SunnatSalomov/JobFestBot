package org.example.jobFestBot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.example.jobFestBot.model.Database;
import org.example.jobFestBot.model.Job;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class JobService implements BaseService<Job> {
    private static final String PATH = "src/main/resources/jobs.json";
    @SneakyThrows
    @Override
    public Job add(Job job) {
        job.setId(UUID.randomUUID().toString());

        objectMapper.writeValue(new File(PATH), job);

        return job;
    }

    @SneakyThrows
    @Override
    public List<Job> getAllList() {
        return objectMapper.readValue(new File(PATH), new TypeReference<>() {});
    }

    @Override
    public Job getById(UUID id) {
        for (Job job : getAllList()){
            if (StringUtils.equals(job.getId(), id.toString())){
                return job;
            }
        }
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public Job update(Job job) {
        return null;
    }
}
