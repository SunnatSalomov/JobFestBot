package org.example.jobFestBot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.example.jobFestBot.bot.utils.MyReplyMarkup;
import org.example.jobFestBot.bot.utils.UserConverter;
import org.example.jobFestBot.model.Announcement;
import org.example.jobFestBot.model.CurriculumVitae;
import org.example.jobFestBot.model.Vacancy;
import org.example.jobFestBot.model.User;
import org.example.jobFestBot.service.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import static org.example.jobFestBot.bot.BotConstants.TOKEN;
import static org.example.jobFestBot.bot.BotConstants.USERNAME;

public class MyBot extends TelegramLongPollingBot {
    public static String STATE = "*";
    private static UserService userService = new UserService();
    private static UserConverter userConverter = new UserConverter();
    private static MyReplyMarkup replyMarkup = new MyReplyMarkup();
  private  static   AnnounceService announceService = new AnnounceService();
  private static CurriculumVitaeService curriculumVitaeService = new CurriculumVitaeService();
    public static final String pathCategory = "src/main/resources/category.json";
    public static final List<String> category = List.of("VACANCY", "Share your doubts", "INFO");
    public static final List<String> images = List.of("image_4.jpg", "image_5.jpg", "image_6.jpg");
    private static CategoryService categoryService = new CategoryService();
   private static String stiker = "\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Long chatId = update.getMessage().getChatId();
            boolean exist = userService.existByChatId(chatId);
            if (update.getMessage().hasDocument()) {
                if (curriculumVitaeService.
                        getCurriculumById(String.valueOf(update.getMessage().getChatId()))!=null){
                    if (curriculumVitaeService.
                            getCurriculumById(String.valueOf(update.getMessage().getChatId())).stream().
                            filter(curriculumVitae1 -> curriculumVitae1.getFileName()==null).findFirst().orElse(null)!=null){
                        addFile(update.getMessage());
                    }

                }

            }
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
                    myExecute("src/main/resources/image_1.jpg", chatId,stiker, replyMarkup.createInlineKeyboardMarkup(categoryService.getVacancyByParentId(".")));
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
            CallbackQuery callbackQuery = update.getCallbackQuery();

            ReplyKeyboard replyKeyboard = replyMarkup.crateInlineCallbackData(callbackQuery.getData());
            myExecute("src/main/resources/image_3.jpg", callbackQuery.getFrom().getId(),
                    stiker, replyKeyboard);
        }

    }
    private void addFile(Message message) {
        try {
            GetFile getFile = new GetFile(message.getDocument().getFileId());
            org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);
            String fileURL = file.getFileUrl(getBotToken());
            URL url = new URL(fileURL);
            InputStream inputStream = url.openStream();
            List<CurriculumVitae> curriculumById = curriculumVitaeService.getCurriculumById(String.valueOf(message.getChatId()));
            CurriculumVitae curriculumVitae= curriculumById.stream().
                    filter(curriculumVitae1 -> curriculumVitae1.getFileName()==null).findFirst().orElse(null);
            if (curriculumVitae!=null && curriculumVitaeService.setFineName(
                    String.valueOf(message.getChatId()),message.getDocument().getFileName())){
            java.io.File destination = new java.io.File("src/main/resources/" + message.getDocument().getFileName());
            org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, destination);
           }
        }  catch (Exception e) {
            e.printStackTrace();
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
    private boolean hasAnnouncement(String id){
      return !categoryService.getVacancyByParentId(id).isEmpty();

    }
    private boolean hasCV(String id){
          return   categoryService.getVacancyByParentId(categoryService.getVacancyByParentId(id).toString()).isEmpty();
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
        Vacancy add = new Vacancy(UUID.randomUUID(), "++++++", category1.getId().toString());
        List<Vacancy> vacancies = new ArrayList<>();
        Collections.addAll(vacancies, category1, category2, category3, category4, category5, add);
        ObjectMapper objectMapper = new ObjectMapper();
        String writeValueAsString = objectMapper.writeValueAsString(vacancies);

        FileUtils.write(pathCategory, writeValueAsString);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MyBot());
    }
}
