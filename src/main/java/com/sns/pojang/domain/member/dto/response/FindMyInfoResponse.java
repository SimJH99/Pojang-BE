package com.sns.pojang.domain.member.dto.response;

import com.sns.pojang.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FindMyInfoResponse {
    private Long id;
    private String nickName;
    private String email;
    private String phoneNumber;

    public static FindMyInfoResponse from(Member member){
        return FindMyInfoResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickName(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}
