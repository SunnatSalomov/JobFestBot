package org.example.jobFestBot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {

    private String name;
    private String emailAdres;
    private String vacancy;
    private String telNumber;
    private String price;
}
