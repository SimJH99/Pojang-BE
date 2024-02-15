package com.sns.pojang.domain.member.dto.response;

import com.sns.pojang.domain.member.entity.Address;
import com.sns.pojang.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindAddressResponse {
    private Address address;

    public static FindAddressResponse from(Member member){
        return FindAddressResponse.builder()
                .address(member.getAddress())
                .build();
    }
}
