package org.example.jobFestBot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.jobFestBot.model.CurriculumVitae;
import org.example.jobFestBot.model.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.example.jobFestBot.service.BaseService.objectMapper;

public class CurriculumVitaeService {
    private static final String path= "src/main/resources/curriculumVitae.json";
    @SneakyThrows
    public CurriculumVitae add(CurriculumVitae curriculumVitae) {
        List<CurriculumVitae> curriculums= getCurriculum();
        curriculums.add(curriculumVitae);
        String valueAsString =objectMapper.writeValueAsString(curriculums);
        FileUtils.write(new File(Path.of(path).toUri()),valueAsString);
        return curriculumVitae;
    }
    @SneakyThrows
    private List<CurriculumVitae> getCurriculum() {
        return objectMapper.readValue(Files.readString(Path.of(path)), new TypeReference<>(){});

    }

    public List<CurriculumVitae> getAllList() throws IOException {
        return objectMapper.readValue(Files.readString(Path.of(path)), new TypeReference<>(){});
    }

    public User getById(String id) {
        return null;
    }

    public void delete(String id) {

    }

    public User update(User user) {
        return null;
    }

    public List<CurriculumVitae> getCurriculumByAnnounceName(String name) {
        List<CurriculumVitae> users = getCurriculum();
        return users.stream().filter(user -> Objects.equals(user.getAnnounceName(), name)).toList();
    }
    public List<CurriculumVitae> getCurriculumById(String id) {
        List<CurriculumVitae> users = getCurriculum();
        return users.stream().filter(user -> Objects.equals(user.getUserId(), id)).toList();
    }
    @SneakyThrows
    public boolean setFineName(String id,String fileName){
        List<CurriculumVitae> curriculums= getCurriculum();
        List<CurriculumVitae> curriculumById = getCurriculumById(id);
        CurriculumVitae curriculumVitae= curriculumById.stream().
                filter(curriculumVitae1 -> curriculumVitae1.getFileName()==null).findFirst().orElse(null);
        if (curriculumVitae!=null){
        curriculums.remove(curriculumVitae);
        curriculumVitae.setFileName(fileName);
        curriculums.add(curriculumVitae);
        String valueAsString =objectMapper.writeValueAsString(curriculums);
        FileUtils.write(new File(Path.of(path).toUri()),valueAsString);
        return true;}
        return false;
    }
}
