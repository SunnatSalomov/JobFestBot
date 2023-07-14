package org.example.jobFestBot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Category {
    private String  id;
    private String name;
    private String  parentId;
}
