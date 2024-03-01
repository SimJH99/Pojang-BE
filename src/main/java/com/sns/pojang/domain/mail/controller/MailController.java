package com.sns.pojang.domain.mail.controller;

import com.sns.pojang.domain.mail.dto.SendEmailResponse;
import com.sns.pojang.domain.mail.service.EmailService;
import com.sns.pojang.global.config.redis.RedisService;
import com.sns.pojang.global.response.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

import static com.sns.pojang.global.response.SuccessMessage.SEND_EMAIL_SUCCESS;

@RestController
@Slf4j
@RequestMapping("/api/mail")
public class MailController {
    private final RedisService redisService;
    private final EmailService emailService;

    @Autowired
    public MailController(RedisService redisService, EmailService emailService){
        this.redisService = redisService;
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<SuccessResponse<SendEmailResponse>> send(@RequestParam String email) throws NoSuchAlgorithmException {
        String sentAuthCode;
        try {
            String createdCode = emailService.generateRandomNumber();
            log.info("이메일 전송 api 시작");
            sentAuthCode = emailService.sendEmailCode(email, createdCode);
        } catch (MailException e){
            throw new MailSendException("이메일 인증코드 전송에 실패했습니다.");
        }

        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(),
                SEND_EMAIL_SUCCESS.getMessage(), SendEmailResponse.from(email, sentAuthCode)));
    }

    @PostMapping("/verify")
    public ResponseEntity<SuccessResponse<Void>> verify(@RequestParam("email") String email,
                                                        @RequestParam("authCode") String authCode){
        String resultMessage = "";
        String authValue = redisService.getValues(email);
        boolean isAuthCheck = redisService.checkExistsValue(authValue);
        boolean isAuthEqual = authCode.equals(authValue);

        if (isAuthCheck){
            if (isAuthEqual){
                resultMessage = "이메일 인증이 완료되었습니다.";
                redisService.deleteValues(email);
            } else {
                resultMessage = "인증번호가 맞지 않습니다. 다시 확인해주세요";
            }
        } else {
            resultMessage = "이메일 인증시간이 만료되었습니다.";
        }

        return ResponseEntity.ok(SuccessResponse.create(HttpStatus.OK.value(), resultMessage));
    }
}
