package org.example.jobFestBot.model;

import java.util.ArrayList;
import java.util.List;

public interface Database {

    List<User> USERS = new ArrayList<>();

    List<Job> JOBS = new ArrayList<>();

    List<Category> CATEGORIES = new ArrayList<>();

    List<Announcement> ANNOUNCEMENTS  = new ArrayList<>();
}
