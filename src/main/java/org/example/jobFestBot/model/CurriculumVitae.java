package org.example.jobFestBot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurriculumVitae {
    private String id;
    private String userId;
    private String fileName;
    private String announceName;
}
