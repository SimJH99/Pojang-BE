package com.sns.pojang.domain.menuCategoryName.entity;

import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menuCategoryNames")
public class MenuCategoryName extends BaseTimeEntity {
    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //카테고리명
    @Column(nullable = false)
    private String option;

    //삭제여부
    @Column(nullable = false)
    private String deleteYn = "N";
}
