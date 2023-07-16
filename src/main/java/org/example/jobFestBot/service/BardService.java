package org.example.jobFestBot.service;

import com.pkslow.ai.AIClient;
import com.pkslow.ai.GoogleBardClient;
import com.pkslow.ai.domain.Answer;

public class BardService {
    public static String getAnsverFromBard(String que){
        AIClient aiClient = new GoogleBardClient("Ywj8m4nfxX3NJUUHiDHHDAV9snUpmdbV72iROoDfCj9UM0d1WQRliJg1dggL8HaNt9sCaQ.");
        Answer hello = aiClient.ask(que);
        return hello.getChosenAnswer();
    }
}
