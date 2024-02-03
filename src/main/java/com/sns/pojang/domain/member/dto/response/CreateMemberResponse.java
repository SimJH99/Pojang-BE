package com.sns.pojang.domain.member.dto.response;

import com.sns.pojang.domain.member.entity.Address;
import com.sns.pojang.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateMemberResponse {
    private Long id;
    private String email;
    private String nickname;
    private Address address;
    public static CreateMemberResponse from(Member member){
        return CreateMemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .address(member.getAddress())
                .build();
    }
}
