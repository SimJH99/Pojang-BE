package com.sns.pojang.domain.menu.entity;

import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuOptionGroup extends BaseTimeEntity {
    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //카테고리명
    @Column(nullable = false)
    private String name;

    //삭제여부
    @Column(nullable = false)
    private String deleteYn = "N";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @OneToMany(mappedBy = "menuOptionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuOption> menuOptions = new ArrayList<>();

    public MenuOptionGroup(String name, Menu menu){
        this.name = name;
        this.menu = menu;
    }
}
