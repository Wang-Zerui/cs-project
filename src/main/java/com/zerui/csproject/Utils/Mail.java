package com.zerui.csproject.Utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {
    private static Session session;
    public static void initMail() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp-mail.outlook.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.starttls.enable","true");
        prop.put("mail.smtp.auth", "true");
        session = Session.getInstance(prop, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("cs3233project@outlook.com", "Student01");
                    }
        });
    }
    public static void sendMessage(String subject, String message, String email) {
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress("cs3233project@outlook.com"));
            msg.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(email)
            );
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
        } catch (MessagingException ignored) {}
    }
    public static void main(String[] args) {
        initMail();
    }
}
