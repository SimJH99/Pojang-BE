package com.sns.pojang.domain.member.dto.request;

import com.sns.pojang.domain.member.entity.Address;
import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.entity.Role;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class CreateMemberRequest {
    @Email(message = "이메일 형식에 맞게 입력해주세요.")
    @NotEmpty(message = "이메일이 비어있으면 안됩니다.")
    private String email;
    @NotEmpty(message = "비밀번호가 비어있으면 안됩니다.")
    private String password;
    @NotEmpty(message = "닉네임이 비어있으면 안됩니다.")
    private String nickname;
    @NotEmpty(message = "전화번호는 비어있으면 안됩니다.")
    private String phoneNumber;
    private String sido; // 시/도
    private String sigungu; // 시/군/구
    private String query; // 도로명

    public Member toEntity(PasswordEncoder passwordEncoder, Role role){
        Address fullAddress = Address.builder()
                .sido(sido)
                .sigungu(sigungu)
                .query(query)
                .build();
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .address(fullAddress)
                .role(role)
                .build();
    }

}
