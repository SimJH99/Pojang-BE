package com.sns.pojang.domain.menu.entity;

import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuOption extends BaseTimeEntity {
    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //카테고리 옵션 이름
    @Column(nullable = false)
    private String name;

    //추가 금액
    @Column(nullable = false)
    private int additionalPrice;

    //삭제여부
    @Column(nullable = false)
    private String deleteYn = "N";
}
