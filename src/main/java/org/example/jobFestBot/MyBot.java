package org.example.jobFestBot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.example.jobFestBot.bot.utils.MyReplyMarkup;
import org.example.jobFestBot.bot.utils.UserConverter;
import org.example.jobFestBot.model.Announcement;
import org.example.jobFestBot.model.Vacancy;
import org.example.jobFestBot.model.User;
import org.example.jobFestBot.service.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.example.jobFestBot.bot.BotConstants.TOKEN;
import static org.example.jobFestBot.bot.BotConstants.USERNAME;

public class MyBot extends TelegramLongPollingBot {
    public static String STATE = "*";
    private static UserService userService = new UserService();
    private static UserConverter userConverter = new UserConverter();
    private static MyReplyMarkup replyMarkup = new MyReplyMarkup();
    public static final String pathCategory = "src/main/resources/category.json";
    public static final List<String> category = List.of("VACANCY", "Share your doubts", "INFO");
    public static final List<String> images = List.of("image_4.jpg", "image_5.jpg", "image_6.jpg");
    private static CategoryService categoryService = new CategoryService();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            boolean exist = userService.existByChatId(chatId);
            if (update.getMessage().hasContact()) {
                User user = userConverter.convertToEntity(update.getMessage().getContact());
                userService.add(user);
                baseMenu(chatId, "successfully registered");
                return;
            }
            if (update.getMessage().hasText()) {

                if (!exist) {
                    myExecute(chatId, "Enter your phone number", replyMarkup.shareContact("Share contact"));
                    return;
                }

                String text = update.getMessage().getText();
                if (StringUtils.equals(text, "/start")) {
                    baseMenu(chatId, "Choose category");
                    STATE = "*";
                }
                if (StringUtils.equals(text, "VACANCY")) {
                    myExecute("src/main/resources/image_1.jpg", chatId,"\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47" +
                            "\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47", replyMarkup.createInlineKeyboardMarkup(categoryService.getVacancyByParentId(".")));
                    STATE = "*";
                    return;
                }
                if (StringUtils.equals(text, "INFO")) {
                    STATE = "*";
                    myExecute("src/main/resources/image_2.jpg", chatId, "Through this bot you can find several" +
                            " available vacancies in the IT field. Start your career with us. Don't be afraid to dream!!!", null);
                }

                if (StringUtils.equals(STATE, "BARD")) {
                    String ansverFromBard = BardService.getAnsverFromBard(text);
                    myExecuteMessage(chatId, ansverFromBard);
                }
                if (StringUtils.equals(text, "Share your doubts")) {
                    myExecuteMessage(chatId, "Any question");
                    STATE = "BARD";
                }
            }
        }
        if (update.hasCallbackQuery()) {
            AnnounceService announceService = new AnnounceService();
            CallbackQuery callbackQuery = update.getCallbackQuery();
            if (categoryService.getVacancyByParentId(callbackQuery.getData()).isEmpty()) {
                categoryService.getVacancyById(callbackQuery.getData()).getName();
                for (int i = 0; i < new Random().nextInt(10); i++) {
                    myExecute("src/main/resources/image_" + new Random().nextInt(2,6)+".jpg",
                            callbackQuery.getFrom().getId(),
                            categoryService.getVacancyById(callbackQuery.getData()).getName()+"   "
                                    +categoryService.getVacancyById(categoryService.getVacancyById(callbackQuery.getData()).getParentId()).getName()+"\n\n"
                                    +announceService.getAnnounce(), null);
                }

                return;
            }
            ReplyKeyboard replyKeyboard = replyMarkup.crateInlineCallbackData(callbackQuery.getData());
            myExecute("src/main/resources/image_3.jpg", callbackQuery.getFrom().getId(),
                    "\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47" +
                            "\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47", replyKeyboard);
        }
    }

    private void myExecute(Long chatId, String message, ReplyKeyboard r) {
        SendMessage s = new SendMessage();
        s.setChatId(chatId);
        s.setText(message);
        s.setReplyMarkup(r);
        try {
            execute(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void myExecuteMessage(Long chatId, String message) {
        SendMessage s = new SendMessage();
        s.setChatId(chatId);
        s.setText(message);
        try {
            execute(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void myExecute(String image, Long chatId, String message, ReplyKeyboard r) {
        SendPhoto s = new SendPhoto();
        s.setChatId(chatId);
        s.setCaption(message);
        s.setPhoto(new InputFile(new File(image)));
        s.setReplyMarkup(r);
        try {
            execute(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void myEdite(Long chatId, Integer m, String message, ReplyKeyboard r) {
        EditMessageText s = new EditMessageText();
        s.setText(message);
        s.setChatId(chatId);
        s.setMessageId(m);
        s.setReplyMarkup((InlineKeyboardMarkup) r);
        try {
            execute(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void baseMenu(Long id, String text) {
        myExecute(id, text, replyMarkup.createReplyKeyboardMarkup(category));
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    public static void main(String[] args) throws IOException, TelegramApiException {
        Vacancy category1 = new Vacancy(UUID.randomUUID(), "Developer", ".");
        Vacancy category2 = new Vacancy(UUID.randomUUID(), "Network Administrator", ".");
        Vacancy category3 = new Vacancy(UUID.randomUUID(), "Database Administrator", ".");
        Vacancy category4 = new Vacancy(UUID.randomUUID(), "Cybersecurity Specialist", ".");
        Vacancy category5 = new Vacancy(UUID.randomUUID(), "ALL VACANCIES", ".");
        Vacancy java = new Vacancy(UUID.randomUUID(), "JAVA", category1.getId().toString());
        Vacancy swift = new Vacancy(UUID.randomUUID(), "Swift", category1.getId().toString());
        Vacancy python = new Vacancy(UUID.randomUUID(), "Python", category1.getId().toString());
        Vacancy net = new Vacancy(UUID.randomUUID(), ".NET", category1.getId().toString());
        Vacancy php = new Vacancy(UUID.randomUUID(), "PHP", category1.getId().toString());

        Vacancy networkDesign = new Vacancy(UUID.randomUUID(), "Network design", category2.getId().toString());
        Vacancy networkSecurity = new Vacancy(UUID.randomUUID(), "Network security", category2.getId().toString());
        Vacancy networkMaintenance = new Vacancy(UUID.randomUUID(), "Network maintenance", category2.getId().toString());

        Vacancy dataCleaning = new Vacancy(UUID.randomUUID(), "Data cleaning", category3.getId().toString());
        Vacancy dataAnalysis = new Vacancy(UUID.randomUUID(), "Data analysis", category3.getId().toString());
        Vacancy databaseDesigner = new Vacancy(UUID.randomUUID(), "Database designer", category3.getId().toString());

        List<Vacancy> vacancies = new ArrayList<>();
        Collections.addAll(vacancies, category1, category2, category3, category4, category5, java, swift, python, php
                , net, networkDesign, networkSecurity, networkMaintenance, dataCleaning, dataAnalysis, databaseDesigner);
        ObjectMapper objectMapper = new ObjectMapper();
        String writeValueAsString = objectMapper.writeValueAsString(vacancies);

        FileUtils.write(pathCategory, writeValueAsString);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MyBot());
    }
}
