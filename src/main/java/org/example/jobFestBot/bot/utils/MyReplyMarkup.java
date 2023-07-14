package org.example.jobFestBot.bot.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class MyReplyMarkup {


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

    public ReplyKeyboard createInlineKeyboardMarkup(List<String> inline) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 1; i <= inline.size(); i++) {
            InlineKeyboardButton button = new InlineKeyboardButton(inline.get(i - 1));
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
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }
}