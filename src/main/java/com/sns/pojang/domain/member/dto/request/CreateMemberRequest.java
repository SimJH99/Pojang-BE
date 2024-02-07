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
    private String postCode;
    private String address;
    private String detailAddress;

    public Member toEntity(PasswordEncoder passwordEncoder){
        Address fullAddress = Address.builder()
                .sido(postCode)
                .sigungu(address)
                .query(detailAddress)
                .build();
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .address(fullAddress)
                .role(Role.ROLE_USER)
                .build();
    }

}
