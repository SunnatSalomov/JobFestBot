package org.example.jobFestBot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Job {
    private String  id;
    private Category category;
    private String name;
    private String description;
    private Double salary;
}
