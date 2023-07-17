package org.example.jobFestBot.service;

import org.example.jobFestBot.model.LikeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LikeService {
    private static List<LikeModel> likeModelList = new ArrayList<>();

    public static void likeEdit(Long chatID, String email) {
        System.out.println(likeModelList);
        Optional<LikeModel> first = likeModelList.stream()
                .filter(likeModel -> likeModel.getChatId() == chatID && likeModel.getEmail().equals(email))
                .findFirst();

        if (first.isPresent()) {
            first.get().setLikeCount(1);
        } else {
            LikeModel likeModel = new LikeModel(chatID, email);
            likeModel.setLikeCount(1);
            likeModelList.add(likeModel);
        }
    }

    public static void disLikeEdit(Long chatID, String email) {
        System.out.println(likeModelList);
        Optional<LikeModel> first = likeModelList.stream().filter(likeModel -> likeModel.getChatId() == chatID && likeModel.getEmail().equals(email)).findFirst();
        if (first.isPresent()) {
            first.get().setLikeCount(0);
        } else {
            LikeModel likeModel = new LikeModel(chatID, email);
            likeModel.setLikeCount(0);
            likeModelList.add(likeModel);
        }
    }

    public static int getCount(Long chatID, String email) {
        List<LikeModel> list = likeModelList.stream()
                .filter(likeModel -> Objects.equals(likeModel.getEmail(), email))
                .toList();
        int sum = 0;
        if (!list.isEmpty()) {
            for (LikeModel likeModel : list) {
                sum += likeModel.getLikeCount();
            }
            return sum;
        }

        return 0;
    }
}
