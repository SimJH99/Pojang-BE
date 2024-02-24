package com.sns.pojang.domain.mail.service;

import com.sns.pojang.global.config.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final RedisService redisService;
    private final MailProperties mailProperties;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, RedisService redisService, MailProperties mailProperties) {
        this.javaMailSender = javaMailSender;
        this.redisService = redisService;
        this.mailProperties = mailProperties;
    }

    @Async
    public void sendEmail(String from, String to, String title, String contents) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(title);
        mailMessage.setText(contents);

        javaMailSender.send(mailMessage);
    }

    @Async
    public String sendEmailCode(String email, String authCode) {
        Duration duration = Duration.ofMinutes(3);
        redisService.setValues(email, authCode, duration);

        sendEmail(mailProperties.getUsername(), email, "포장의 민족에서 발송한 인증번호를 확인해주세요.", authCode);

        return authCode;
    }

    public String generateRandomNumber() throws NoSuchAlgorithmException {
        String result;

        do {
            int num = SecureRandom.getInstanceStrong().nextInt(999999);
            result = String.valueOf(num);
        } while (result.length() != 6);

        return result;
    }
}
