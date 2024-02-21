package com.sns.pojang.domain.member.dto.response;

import com.sns.pojang.domain.member.entity.Address;
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
    private String address;
    public static FindMyInfoResponse from(Member member){
        Address address = member.getAddress();
        String sido = address.getSido();
        String sigungu = address.getSigungu();
        String query= address.getQuery();

        return FindMyInfoResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickName(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .address(sido + " " + sigungu + " " + query)
                .build();
    }
}
