package org.example.jobFestBot;

import org.apache.commons.lang3.StringUtils;
import org.example.jobFestBot.bot.utils.MyReplyMarkup;
import org.example.jobFestBot.bot.utils.UserConverter;
import org.example.jobFestBot.model.User;
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
    private static UserService userService = new UserService();
    private static UserConverter userConverter = new UserConverter();
    private static MyReplyMarkup replyMarkup = new MyReplyMarkup();
    public static final String pathCategory = "src/main/resources/category.json";
   public static final List<String> category = List.of("VACANCY", "TO ANNOUNCE", "INFO");
   public static final List<String> vacancies = List.of("Developer","Network Administrator",
                   "Database Administrator","Cybersecurity Specialist","Data Analyst");
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {

            Long chatId = update.getMessage().getChatId();
            boolean exist = userService.existByChatId(chatId);
            if (update.getMessage().hasText()){
                String text = update.getMessage().getText();
            if (!exist) {
                    ReplyKeyboard shareContact = replyMarkup.shareContact("Shate contact");
                    myExecute(chatId, "Enter your phone number", shareContact);
                    return;
            }
                if (StringUtils.equals(text, "/start")) {
                    baseMenu(chatId, "Welcome to our bot !");
                }
                if (StringUtils.equals(text,category.get(0))){
                    System.out.println(category.get(0));
                    myExecute("src/main/resources/image_1.jpg",chatId,"Please select the profession you need",replyMarkup.createInlineKeyboardMarkup(vacancies));
                    return;
                }
                if (StringUtils.equals(text,category.get(1))){

                }
                if (StringUtils.equals(text,category.get(2))){
               myExecute(chatId,"",null);
                }
            }
            System.out.println(update.getMessage().hasReplyMarkup());
            if (update.getMessage().hasContact()) {
                User user = userConverter.convertToEntity(update.getMessage().getContact());
                userService.add(user);
                baseMenu(chatId,"successfully registered");
                return;
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
    private void baseMenu(Long id, String text){
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
