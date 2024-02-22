package com.sns.pojang.domain.member.dto.response;

import com.sns.pojang.domain.member.entity.Address;
import com.sns.pojang.domain.member.entity.Member;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
public class UpdateMyInfoResponse {
    @NotEmpty(message = "닉네임이 비어있으면 안됩니다.")
    private String nickname;
    @NotEmpty(message = "전화번호는 비어있으면 안됩니다.")
    private String phoneNumber;

    public static UpdateMyInfoResponse from(Member member){
        return UpdateMyInfoResponse.builder()
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
