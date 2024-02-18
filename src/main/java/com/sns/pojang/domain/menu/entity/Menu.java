package com.sns.pojang.domain.menu.entity;

import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    //메뉴 이미지 경로
    private String imageUrl;

    //매진여부
    @Column(nullable = false)
    private String soldOutYn = "N";

    //삭제여부
    @Column(nullable = false)
    private String deleteYn = "N";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuOptionGroup> menuOptionGroups = new ArrayList<>();

    @Builder
    public Menu(String name, String menuInfo, int price, String imageUrl, Store store){
        this.name = name;
        this.menuInfo = menuInfo;
        this.price = price;
        this.imageUrl = imageUrl;
        this.store = store;
    }

    public Menu updateMenu(String name,
                           String menuInfo,
                           int price,
                           String imageUrl,
                           Store store){
        this.name = name;
        this.menuInfo = menuInfo;
        this.price = price;
        this.imageUrl = imageUrl;
        this.store = store;

        return this;
    }

    public void updateDeleteYn(){
        this.deleteYn = "Y";
    }
}
