package com.sns.pojang.domain.member.dto.response;

import com.sns.pojang.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindEmailResponse {
    private String email;

    public static FindEmailResponse from(Member member){
        return FindEmailResponse.builder()
                .email(member.getEmail())
                .build();
    }
}
