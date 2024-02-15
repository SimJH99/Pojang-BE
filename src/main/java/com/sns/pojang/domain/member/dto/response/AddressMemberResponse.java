package com.sns.pojang.domain.member.dto.response;

import com.sns.pojang.domain.member.entity.Address;
import com.sns.pojang.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressMemberResponse {
    private Address address;

    public static AddressMemberResponse from(Member member){
        return AddressMemberResponse.builder()
                .address(member.getAddress())
                .build();
    }
}
