package com.sns.pojang.domain.mail.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SendEmailResponse {
    private String email;
    private String authCode;

    public static SendEmailResponse from(String email, String authCode){
        return SendEmailResponse.builder()
                .email(email)
                .authCode(authCode)
                .build();
    }
}
