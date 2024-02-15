package com.sns.pojang.domain.member.dto.request;

import lombok.Data;

@Data
public class UpdateAddressRequest {
    private String sido; // 시/도
    private String sigungu; // 시/군/구
    private String query; // 도로명
}
