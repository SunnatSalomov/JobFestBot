package org.example.jobFestBot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.jobFestBot.model.User;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserService implements BaseService<User> {
    private static final String path= "src/main/resources/user.json";
    @SneakyThrows
    @Override
    public User add(User user) {
        List<User> users = getUser();
        existUser(user,users);
        user.setId(UUID.randomUUID().toString());
         users.add(user);
        String valueAsString = objectMapper.writeValueAsString(user);
        FileUtils.write(new File(path),valueAsString);
        return user;
    }

    private void existUser(User user, List<User> users) {
        users.stream().filter(user1 -> StringUtils.equals(user1.getPhoneNumber(),user.getPhoneNumber())).findFirst().ifPresent(u->{
            new IllegalAccessException(String.format("%s  user exists ",u.getFirstName()));
        });
    }

    @SneakyThrows
    private List<User> getUser() {
        return objectMapper.readValue(new File(path), new TypeReference<>(){});

    }

    @Override
    public List<User> getAllList() {
        return null;
    }

    @Override
    public User getById(String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }



    @Override
    public User update(User user) {
        return null;
    }

    public boolean existByChatId(Long chatId) {
        List<User> users = getUser();
        return users.stream().anyMatch(user -> Objects.equals(user.getChatId(), chatId));

    }
}