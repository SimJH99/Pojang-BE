package com.sns.pojang.domain.member.dto.request;

import lombok.Data;

@Data
public class SmsCertificationRequest {
    private String phoneNumber;
    private String certificationNumber;
}
