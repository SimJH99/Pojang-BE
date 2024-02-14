package com.sns.pojang.domain.menu.entity;

import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseTimeEntity{
    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //메뉴이름
    @Column(nullable = false)
    private String name;

    //메뉴정보
    private String menuInfo;

    //가격
    @Column(nullable = false)
    private int price;

    //메뉴 사진
    private String imageUrl;

    //매진여부
    @Column(nullable = false)
    private String soldOutYn = "N";

    //삭제여부
    @Column(nullable = false)
    private String deleteYn = "N";
}
