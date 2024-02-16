package com.sns.pojang.domain.menuCategory.entity;

import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "menuCategories")
public class MenuCategory extends BaseTimeEntity {
    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //카테고리명
    @Column(nullable = false)
    private String menuCategoryName;

    //삭제여부
    @Column(nullable = false)
    private String deleteYn = "N";
}
