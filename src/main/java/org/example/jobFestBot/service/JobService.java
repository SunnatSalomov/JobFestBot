package org.example.jobFestBot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.example.jobFestBot.model.Job;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class JobService implements BaseService<Job> {
    private static final String PATH = "src/main/resources/job.json";

    @SneakyThrows
    @Override
    public void add(Job job) {
        //  job.setId(UUID.randomUUID().toString());

        objectMapper.writeValue(new File(PATH), job);

    }

    @SneakyThrows
    @Override
    public List<Job> getAllList() {
        return objectMapper.readValue(new File(PATH), new TypeReference<>() {
        });
    }

    @Override
    public Job getById(String id) {
        for (Job job : getAllList()) {
            if (StringUtils.equals(job.getId(), id)) {
                return job;
            }
        }
        return null;
    }

    @SneakyThrows
    @Override
    public void delete(String id) {
        List<Job> jobs = objectMapper.readValue(new File(PATH), new TypeReference<List<Job>>() {
        });
        for (Job job : jobs) {
            if (StringUtils.equals(job.getId(), id)) {
                jobs.remove(job);
                break;
            }
        }
        objectMapper.writeValue(new File(PATH), jobs);
    }

    @SneakyThrows
    @Override
    public Job update(Job job) {
        List<Job> jobs = objectMapper.readValue(new File(PATH), new TypeReference<List<Job>>() {
        });
        for (Job j : jobs) {
            if (StringUtils.equals(j.getId(), job.getId())) {
                jobs.remove(j);
                break;
            }
        }
        jobs.add(job);
        objectMapper.writeValue(new File(PATH), jobs);
        return job;
    }

}