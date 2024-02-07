package com.sns.pojang.domain.menuOption.entity;

import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MenuOptions")
public class MenuOption extends BaseTimeEntity {
    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //옵션이름
    @Column(nullable = false)
    private String option;

    //내용
    @Column(nullable = false)
    private String content;

    //가격
    @Column(nullable = false)
    private int price;

    //삭제여부
    @Column(nullable = false)
    private String deleteYn = "N";
}
