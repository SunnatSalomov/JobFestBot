package org.example.jobFestBot.bot.utils;

import org.apache.commons.lang3.StringUtils;
import org.example.jobFestBot.MyBot;
import org.example.jobFestBot.model.Vacancy;
import org.example.jobFestBot.service.CategoryService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MyReplyMarkup {
    private static CategoryService categoryService = new CategoryService();
    public static final List<String> developerMenu = List.
            of("Swift", "Kotlin", "Java","HTML and CSS");
    public static final List<String> networkAdministratorMenu = List.
            of("Network design", "Network security",
                    "Network maintenance","Network troubleshooting","Network documentation");
    public static final List<String> dataAnalystMenu = List.of("Data cleaning", "Data analysis",
            "Data modeling", "Data reporting", "Data visualization", "Business intelligence");
    public static final List<String> databaseAdministratorMenu = List.of("Database design", "Database security",
            "Database backup and recovery", "Database performance tuning",
            "Database administration");
    public ReplyKeyboard createReplyKeyboardMarkup(List<String> category) {
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();

        KeyboardRow row = new KeyboardRow();
        List<KeyboardRow> rows = new ArrayList<>();
        for (int i = 1; i <= category.size(); i++) {
            row.add(new KeyboardButton(category.get(i - 1)));
            if (i % 2 == 0) {
                rows.add(row);
                row = new KeyboardRow();
            }
        }

        if (category.size() % 2 != 0) {
            rows.add(row);
        }
        replyKeyboard.setKeyboard(rows);
        replyKeyboard.setResizeKeyboard(true);
        return replyKeyboard;
    }

    public ReplyKeyboard createInlineKeyboardMarkup(List<Vacancy> inline) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 1; i <= inline.size(); i++) {
            InlineKeyboardButton button = new InlineKeyboardButton(inline.get(i - 1).getId().toString());
            button.setCallbackData(String.valueOf(i - 1));
            row.add(button);
            if (i % 2 == 0) {
                rows.add(row);
                row = new ArrayList<>();
            }
        }

        if (inline.size() % 2 != 0) {
            rows.add(row);
        }
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public ReplyKeyboard createInlineKeyboardMarkupURL(String inline) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
        keyboardButton.setText(inline);
        keyboardButton.setCallbackData("nn");
        keyboardButton.setUrl("https://getmatch.ru/vacancies?p=1&sa=250000&l=moscow&l=saints_p&pl=python&sp=dev_ops&pa=all");
        row.add(keyboardButton);
        rows.add(row);
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public ReplyKeyboard shareContact(String title) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setRequestContact(true);
        keyboardButton.setText(title);
        row.add(keyboardButton);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(row);

        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }
        public ReplyKeyboard crateInlineCallbackData(String callbackData) {
            List<Vacancy> list = categoryService.getAllList().stream().filter(vacancy -> Objects.equals(callbackData, vacancy.getParentId())).toList();
            return createInlineKeyboardMarkup(list);
        }
}
