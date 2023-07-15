package org.example.jobFestBot.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class EmailService {
    public static String getGenerateNumberSendGmailToMessage(String gmail) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        String username = "no0404ir@gmail.com";
        String password = "xfkwetwkblkszcwr";

        Session session = getInstance(properties, username, password);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setSubject("Activate code");
            int i = new Random().nextInt(100, 1000);
            int i1 = new Random().nextInt(100, 1000);
            message.setText("Activate code: "+i + "" + i1);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(gmail));
            Transport.send(message);
            System.out.println("message send");

            return i + "" + i1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static Session getInstance(Properties properties, String username, String password) {
        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}
