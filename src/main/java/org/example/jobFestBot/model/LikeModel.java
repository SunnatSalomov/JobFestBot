package org.example.jobFestBot.model;

import lombok.Data;

@Data
public class LikeModel {
    private long chatId;
    private int likeCount = 0;
    private String messageID;

    public LikeModel(long chatId, String messageID) {
        this.chatId = chatId;
        this.messageID = messageID;
    }
}
