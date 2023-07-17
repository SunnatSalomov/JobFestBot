package org.example.jobFestBot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
public class Job {
    private   final String  id = UUID.randomUUID().toString() ;
   private Announcement announcement;

    public Job( Announcement announcement) {
        this.announcement = announcement;
    }
}


