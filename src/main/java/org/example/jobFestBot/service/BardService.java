package org.example.jobFestBot.service;

import com.pkslow.ai.AIClient;
import com.pkslow.ai.GoogleBardClient;
import com.pkslow.ai.domain.Answer;

public class BardService {
    public static String getAnsverFromBard(String que) {
        AIClient aiClient = new GoogleBardClient("YQi9-gL2cRtXUZDa8oPBMH5ddt6-KxeIp0U9JdPtQ22352NXEz1V3pWy9ufAiCxU76LNWg.");
        Answer hello = aiClient.ask(que);
        return hello.getChosenAnswer();
    }
}
