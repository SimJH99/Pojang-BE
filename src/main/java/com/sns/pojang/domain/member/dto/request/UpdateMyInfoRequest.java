package com.sns.pojang.domain.member.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateMyInfoRequest {
    @NotEmpty(message = "닉네임이 비어있으면 안됩니다.")
    private String nickname;
    @NotEmpty(message = "전화번호는 비어있으면 안됩니다.")
    private String phoneNumber;
}
