package org.example.jobFestBot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.example.jobFestBot.model.Announcement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;


public class AnnounceService implements BaseService<Announcement> {
    private static final String path = "src/main/resources/announce.json";

    @SneakyThrows
    @Override
    public void add(Announcement announce) {
        List<Announcement> announcement = getAnnouncement();
        announcement.add(announce);
        String json = objectMapper.writeValueAsString(announcement);
        FileUtils.write(path, json);
    }

    @SneakyThrows
    private static List<Announcement> getAnnouncement() {
        return objectMapper.readValue(new File(path), new TypeReference<>() {
        });
    }

    @Override
    public List<Announcement> getAllList() throws IOException {
        return objectMapper.readValue(Files.readString(Path.of(path)), new TypeReference<>() {
        });
    }

    @Override
    public Announcement getById(String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Announcement update(Announcement announceService) {
        return null;
    }

    @SneakyThrows
    public Announcement getAnnounce(){
        List<Announcement> allList = getAllList();
        return allList.get(3);//new Random().nextInt(3));
    }
}
