package com.psionicgeek.demobankingapp.MailSender;

import com.psionicgeek.demobankingapp.config.MailConfig;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailSender {
    public static void httpEmailSender(String fromEmail, String toEmail, String subject, String body) {
        JavaMailSenderImpl mailSender = MailConfig.getJavaMailSender();
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            message.setContent(body, "text/html");

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
