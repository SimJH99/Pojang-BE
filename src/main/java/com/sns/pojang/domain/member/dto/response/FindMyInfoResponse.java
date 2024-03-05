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
    private String sido;
    private String sigungu;
    private String bname;
    private String roadAddress;
    public static FindMyInfoResponse from(Member member){
        Address address = member.getAddress();
        String sido = address.getSido();
        String sigungu = address.getSigungu();
        String bname = address.getBname();
        String roadAddress = address.getRoadAddress();

        return FindMyInfoResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickName(member.getNickname())
                .sido(sido)
                .sigungu(sigungu)
                .bname(bname)
                .phoneNumber(member.getPhoneNumber())
                .roadAddress(roadAddress)
                .build();
    }
}
