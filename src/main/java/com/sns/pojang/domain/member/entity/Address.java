package com.sns.pojang.domain.member.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable // 이 객체를 어딘가의 객체에 중간에 삽입시킨다.
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String postCode; // 우편번호
    private String address; // 주소: 서울 00구 00로 0길
    private String detailAddress; // 상세주소: 0동 0호
}
