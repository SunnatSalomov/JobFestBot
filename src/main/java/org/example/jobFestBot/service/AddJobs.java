package org.example.jobFestBot.service;


import org.apache.commons.lang3.StringUtils;
import org.example.jobFestBot.model.Announcement;
import org.example.jobFestBot.model.Job;
import org.example.jobFestBot.model.Vacancy;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AddJobs {
    public static JobService jobService = new JobService();
    private static CategoryService categoryService = new CategoryService();
    private static AnnounceService announceService = new AnnounceService();
    private static String path = "src/main/resources/announces.json";

    public void addAnnounceToJob() throws IOException {
        List<Job> jobList = jobService.getAllList();
        List<Vacancy> vacancies = categoryService.getAllList();

        while (true) {
            for (Vacancy vacancy : vacancies) {
                // System.out.println(vacancy);
            }
            String enterTheVacancy = ScannerUtil.getString("Enter the vacancy :");
            boolean match = vacancies.stream().anyMatch(vacancy -> StringUtils.equals(vacancy.getName(), enterTheVacancy));
            if (!match) {
                throw new InputMismatchException("Not found ");
            }
            String enterTheCompanyName = ScannerUtil.getString("Enter the Company name :");
            String enterTheSalary = ScannerUtil.getString("Enter the salary :");
            String emailAddress = ScannerUtil.getString("Email Address :");
            String enterTheTelNumber = ScannerUtil.getString("Enter the tel number :");

            Announcement announcement = new Announcement(enterTheCompanyName, emailAddress, enterTheVacancy, enterTheTelNumber, enterTheSalary);

            List<Announcement> allList2 = announceService.getAllList();
            allList2.add(announcement);
            announceService.add(announcement);
        }


    }


    private static class ScannerUtil {
        public static String getString(String s) {
            System.out.printf(s);
            return new Scanner(System.in).nextLine();
        }

        public static Double getDouble(String enterPrice) {
            return null;
        }
    }
}
