package org.example;

import org.example.jobFestBot.MyBot;
import org.example.jobFestBot.service.AddJobs;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws TelegramApiException, IOException {
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
       telegramBotsApi.registerBot(new MyBot());
        AddJobs addJobs = new AddJobs();
        addJobs.addAnnounceToJob();
    }
}