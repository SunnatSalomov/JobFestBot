package org.example.jobFestBot.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Job {
    private   final String  id = UUID.randomUUID().toString() ;
   private Announcement announcement;

    public Job( Announcement announcement) {
        this.announcement = announcement;
    }
}


