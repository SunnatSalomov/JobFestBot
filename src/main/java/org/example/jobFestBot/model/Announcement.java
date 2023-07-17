package org.example.jobFestBot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Announcement {
    private String announceId;
    private String name;
    private String emailAdres;
    private String vacancy;
    private String telNumber;
    private String price;

    public Announcement(String name, String emailAdres, String vacancy, String telNumber, String price) {
        this.announceId = UUID.randomUUID().toString();
        this.name = name;
        this.emailAdres = emailAdres;
        this.vacancy = vacancy;
        this.telNumber = telNumber;
        this.price = price;
    }
}
