package com.psionicgeek.demobankingapp.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


@Configuration
public class MailConfig {

    @Bean
    public static JavaMailSenderImpl getJavaMailSender() {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.debug", "true");
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(2500);
        mailSender.setUsername("no-reply@demobank.com");
      mailSender.setPassword("password");
        mailSender.setJavaMailProperties(props);
        return mailSender;
    }
}
