package com.sns.pojang.domain.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateEmailResponse {
    private String email;
    private String certificationNumber;
}
