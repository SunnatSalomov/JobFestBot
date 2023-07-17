package org.example.jobFestBot.model;

import lombok.Data;

@Data
public class LikeModel {
    private long chatId;
    private int likeCount = 0;
    private String email;

    public LikeModel(long chatId, String email) {
        this.chatId = chatId;
        this.email = email;
    }

    public LikeModel(long chatId) {
        this.chatId = chatId;
    }
}
