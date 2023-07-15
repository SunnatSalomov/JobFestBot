package org.example.jobFestBot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String id;
    private Long chatId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String createdDate;
    private String email;
}
