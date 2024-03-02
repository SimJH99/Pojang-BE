package com.sns.pojang.domain.menu.entity;

import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
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

    // 메뉴 옵션 이름
    @Column(nullable = false)
    private String name;

    // 메뉴 옵션 가격
    @Column(nullable = false)
    private int price;

    //삭제 여부
    @Column(nullable = false)
    private String deleteYn = "N";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_option_group_id", nullable = false)
    private MenuOptionGroup menuOptionGroup;

    @Builder
    public MenuOption(String name, int price){
        this.name = name;
        this.price = price;
    }

    public void attachOptionGroup(MenuOptionGroup menuOptionGroup){
        this.menuOptionGroup = menuOptionGroup;
        menuOptionGroup.getMenuOptions().add(this);
    }
}
