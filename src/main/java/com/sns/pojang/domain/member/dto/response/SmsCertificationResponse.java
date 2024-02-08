package com.sns.pojang.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SmsCertificationResponse {
    private String phoneNumber;
    private String certificationNumber;
}
