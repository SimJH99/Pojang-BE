package com.sns.pojang.domain.member.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable // 이 객체를 어딘가의 객체에 중간에 삽입시킨다.
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String sido; // 시/도
    private String sigungu; // 시/군/구
    private String query; // 도로명
}
