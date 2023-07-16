package org.example.jobFestBot;

import org.apache.commons.lang3.StringUtils;
import org.example.jobFestBot.bot.utils.MyReplyMarkup;
import org.example.jobFestBot.bot.utils.UserConverter;
import org.example.jobFestBot.model.User;
import org.example.jobFestBot.service.BardService;
import org.example.jobFestBot.service.UserService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.io.File;
import java.util.List;

import static org.example.jobFestBot.bot.BotConstants.TOKEN;
import static org.example.jobFestBot.bot.BotConstants.USERNAME;

public class MyBot extends TelegramLongPollingBot {
    public static String STATE = "*";
    private static UserService userService = new UserService();
    private static UserConverter userConverter = new UserConverter();
    private static MyReplyMarkup replyMarkup = new MyReplyMarkup();
    public static final String pathCategory = "src/main/resources/category.json";
    public static final List<String> category = List.of("VACANCY", "Share your doubts", "INFO");
    public static final List<String> vacancies = List.of("Developer", "Network Administrator",
            "Database Administrator", "Cybersecurity Specialist", "Data Analyst");
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

                if (exist) {
                    myExecute(chatId, "Enter your phone number", replyMarkup.shareContact("Share contact"));
                    return;
                }

                String text = update.getMessage().getText();
                if (StringUtils.equals(text, "/start")) {
                    baseMenu(chatId, "Choose category");
                    STATE = "*";
                }
                if (StringUtils.equals(text, "VACANCY")) {
                    myExecute("src/main/resources/image_1.jpg", chatId, "Please select the profession you need", replyMarkup.createInlineKeyboardMarkup(vacancies));
                    STATE = "*";
                    return;
                }if (StringUtils.equals(text, "INFO")) {
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