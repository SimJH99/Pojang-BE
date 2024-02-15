package com.sns.pojang.domain.member.dto.request;

import com.sns.pojang.domain.member.entity.Address;
import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class MyInfoMemberRequest {
    @NotEmpty(message = "닉네임이 비어있으면 안됩니다.")
    private String nickname;
    @NotEmpty(message = "비밀번호가 비어있으면 안됩니다.")
    private String password;
    @NotEmpty(message = "전화번호는 비어있으면 안됩니다.")
    private String phoneNumber;
}
