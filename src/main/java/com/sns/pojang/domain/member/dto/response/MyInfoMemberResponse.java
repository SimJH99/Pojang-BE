package com.sns.pojang.domain.member.dto.response;

import com.sns.pojang.domain.member.entity.Address;
import com.sns.pojang.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MyInfoMemberResponse {
    private Long id;
    private String nickName;
    private String email;
    private Address address;

    public static MyInfoMemberResponse from(Member member){
        return MyInfoMemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickName(member.getNickname())
                .address(member.getAddress())
                .build();
    }
}
