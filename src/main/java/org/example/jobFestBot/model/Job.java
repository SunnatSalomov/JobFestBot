package org.example.jobFestBot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Job {
    private String  id;
    private Vacancy category;
    private String name;
    private String description;
    private Double salary;
}
