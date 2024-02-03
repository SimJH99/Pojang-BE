package com.sns.pojang.domain.member.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginMemberRequest {
    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    @NotEmpty(message = "이메일이 비어있으면 안됩니다.")
    private String email;
    @NotEmpty(message = "비밀번호가 비어있으면 안됩니다.")
    private String password;
}
