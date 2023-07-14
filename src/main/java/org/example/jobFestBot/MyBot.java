package org.example.jobFestBot;

import org.apache.commons.lang3.StringUtils;
import org.example.jobFestBot.bot.utils.MyReplyMarkup;
import org.example.jobFestBot.bot.utils.UserConverter;
import org.example.jobFestBot.model.User;
import org.example.jobFestBot.service.CategoryService;
import org.example.jobFestBot.service.UserService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static org.example.jobFestBot.bot.BotConstants.TOKEN;
import static org.example.jobFestBot.bot.BotConstants.USERNAME;

public class MyBot extends TelegramLongPollingBot {
    private static UserService userService = new UserService();
    private static UserConverter userConverter = new UserConverter();
    private static MyReplyMarkup replyMarkup = new MyReplyMarkup();
    public static final String pathCategory = "src/main/resources/category.json";

    public static CategoryService categoryService = new CategoryService();

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Contact contact = update.getMessage().getContact();
            Long chatId = update.getMessage().getChatId();
            boolean exist = userService.existByChatId(chatId);
            if (Objects.nonNull(contact)) {
                User user = userConverter.convertToEntity(contact);
                userService.add(user);
                myExecute(chatId, "successfully registered", replyMarkup.createReplyKeyboardMarkup(List.of("SEARCH JOB", "TO ANNOUNCE", "INFO")));
                return;
            }
            if (!exist) {
                myExecute(chatId, "Welcome to our bot !", replyMarkup.shareContact());
                return;
            }
            if (StringUtils.equals(update.getMessage().getText(), "/start")) {
                myExecute(chatId, "Welcome to our bot !", replyMarkup.createReplyKeyboardMarkup(List.of("SEARCH JOB", "TO ANNOUNCE", "INFO")));
            } else if (StringUtils.equalsAnyIgnoreCase(update.getMessage().getText(), "SEARCH JOB")) {
                myExecute(chatId, "choose category", replyMarkup.createInlineKeyboardMarkup((String) null));
            }
        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            myEdite(chatId, messageId, " child categories ", replyMarkup.createInlineKeyboardMarkup(data));
            // myExecute(chatId, createInlineKeyboardMarkup(categories, data));
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





    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }
}
