package org.example.jobFestBot.service;

import org.example.jobFestBot.model.LikeModel;

import java.util.*;

public class LikeService {
    private static List<LikeModel> likeModelList = new ArrayList<>();
    public static void likeEdit(Long chatID, String messageID){

        LikeModel likeModel = new LikeModel(chatID, messageID);

        if (!likeModelList.contains(likeModel)){
            likeModel.setLikeCount(likeModel.getLikeCount()+1);
            likeModelList.add(likeModel);
        }
        System.out.println("likeModel = " + likeModel);
    }
    public static void disLikeEdit(Long chatID, String messageID) {
        LikeModel likeModel = new LikeModel(chatID, messageID);
        if (!likeModelList.contains(likeModel)){
            likeModel.setLikeCount(likeModel.getLikeCount()-1);
            likeModelList.add(likeModel);
        }else {
            likeModel.setLikeCount(likeModel.getLikeCount()-1);
            likeModelList.add(likeModel);
        }

        System.out.println("likeModel = " + likeModel);
    }
}
