package com.sns.pojang.domain.member.dto.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateMyInfoRequest {
    @NotEmpty(message = "닉네임이 비어있으면 안됩니다.")
    private String nickname;
    @NotEmpty(message = "이메일이 비어있으면 안됩니다.")
    private String email;
    @NotEmpty(message = "비밀번호가 비어있으면 안됩니다.")
    private String password;
    @NotEmpty(message = "전화번호는 비어있으면 안됩니다.")
    private String phoneNumber;
}
