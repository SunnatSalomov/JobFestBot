package org.example.jobFestBot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.example.jobFestBot.model.Announcement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;


public class AnnounceService implements BaseService<Announcement> {
    private static final String path = "src/main/resources/announce.json";

    @Override
    public Announcement add(Announcement announceService) {
        return null;
    }

    @Override
    public List<Announcement> getAllList() throws IOException {
        return objectMapper.readValue(Files.readString(Path.of(path)), new TypeReference<>() {});
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
    public String getAnnounce(){
        List<Announcement> allList = getAllList();
        Announcement announcement = allList.get(new Random().nextInt(allList.size()));
        return "🏢 Company: "+announcement.getName()+"\n \uD83D\uDCCD Address: "+announcement.getVacancy()+"\nSalary : "+announcement.getPrice()+"\n☎\uFE0F "
                +announcement.getEmailAdres()+"\nPhone number :"+announcement.getTelNumber()+
                "\n\n \uD83D\uDC49 Vacancy to post Resume \uD83D\uDC49 @nodir0050\n" +
                "\n" +
                "        Subscribe for daily announcements! \uD83C\uDFAF\n" +
                "\n" +
                "   \n" +
                "Our instagram channel \uD83D\uDC49 https://www.instagram.com/pdpuz/ \uD83D\uDC48";
    }
}
