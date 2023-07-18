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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.util.List;
import java.util.Random;

import static org.example.jobFestBot.bot.BotConstants.*;

public class MyBot extends TelegramLongPollingBot {
    public static String STATE = "*";
    private static UserService userService = new UserService();
    private static UserConverter userConverter = new UserConverter();
    private static MyReplyMarkup replyMarkup = new MyReplyMarkup();
    public static final String pathCategory = "src/main/resources/category.json";
    public static final List<String> category = List.of("VACANCY", "Share your doubts", "INFO");
    public static final List<String> images = List.of("image_4.jpg", "image_5.jpg", "image_6.jpg");
    private static CategoryService categoryService = new CategoryService();
    private static String pathImage = "src/main/resources/image_3.jpg";

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
                    myExecute("src/main/resources/image_1.jpg", chatId, IMOJE, replyMarkup.createInlineKeyboardMarkup(categoryService.getVacancyByParentId(".")));
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
            Long chatId = callbackQuery.getMessage().getChatId();
            String data = callbackQuery.getData();

            if (data.equals("like")) {
                LikeService.likeEdit(chatId, announceService.getAnnounce().getEmailAdres());
                return;
            }
            if (data.equals("dislike")) {
                LikeService.disLikeEdit(chatId, announceService.getAnnounce().getEmailAdres());
                return;
            }
            ReplyKeyboard replyKeyboard = replyMarkup.crateInlineCallbackData(callbackQuery.getData());
            String callbackQueryData = update.getCallbackQuery().getData();
            if (callbackQueryData.startsWith("back")) {
                backButton(update, callbackQueryData);
            } else if (categoryService.getVacancyByParentId(callbackQuery.getData()).isEmpty()) {
                exexuteCatigory(announceService, callbackQuery, chatId);
            } else myExecute(pathImage, callbackQuery.getFrom().getId(), IMOJE, replyKeyboard);
        }
    }

    private void backButton(Update update, String callbackQueryData) {
        String substring = callbackQueryData.substring(4);
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        Vacancy vacancyToBack = categoryService.getById(substring);
        String parentId = vacancyToBack.getParentId();
        List<Vacancy> vacancyByParentId = categoryService.getVacancyByParentId(parentId);
        ReplyKeyboard inlineKeyboardMarkup = replyMarkup.createInlineKeyboardMarkup(vacancyByParentId);
        myExecute(pathImage, chatId, IMOJE, inlineKeyboardMarkup);

    }

    private void exexuteCatigory(AnnounceService announceService, CallbackQuery callbackQuery, Long chatId) {
        String s = String.valueOf(callbackQuery);
        Announcement announce = announceService.getAnnounce();
        for (int i = 0; i < new Random().nextInt(1, 10); i++) {
            myExecute(
                    "src/main/resources/image_" + new Random().nextInt(2, 6) + ".jpg", callbackQuery.getFrom().getId(),
                    "🏢 Company: " + announce.getName() + "\n \uD83D\uDCCD Address: " + announce.getVacancy() + "\nSalary : " + announce.getPrice() + "\n☎️\uFE0F "
                            + announce.getEmailAdres() + "\nPhone number :" + announce.getTelNumber() +
                            "\n\n \uD83D\uDC49 Vacancy to post Resume \uD83D\uDC49 @nodir0050\n" +
                            "\n" +
                            "        Subscribe for daily announcements! \uD83C\uDFAF\n" +
                            "\n" +
                            "   \n" +
                            "Our instagram channel \uD83D\uDC49 https://www.instagram.com/pdpuz/ \uD83D\uDC48" + "   " + " like " + LikeService.getCount(chatId, announceService.getAnnounce().getEmailAdres()), replyMarkup.createInlineKeyboardMarkupLike());
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

}
