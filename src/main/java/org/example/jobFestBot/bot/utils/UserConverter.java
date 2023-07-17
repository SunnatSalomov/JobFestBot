package org.example.jobFestBot.bot.utils;

import org.example.jobFestBot.model.User;
import org.telegram.telegrambots.meta.api.objects.Contact;

public class UserConverter {
    public User convertToEntity(Contact contact) {
        User user = new User(contact.getFirstName(), contact.getLastName(), contact.getPhoneNumber(), null);
        user.setChatId(contact.getUserId());
        return user;
    }
    public void convertAnnounce(String id){

    }
}
