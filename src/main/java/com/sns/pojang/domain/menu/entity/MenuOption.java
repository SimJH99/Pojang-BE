package com.sns.pojang.domain.menu.entity;

import com.sns.pojang.domain.order.entity.OrderMenu;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_menu_id")
    private OrderMenu orderMenu;

    @Builder
    public MenuOption(String name, int price, MenuOptionGroup menuOptionGroup){
        this.name = name;
        this.price = price;
        this.menuOptionGroup = menuOptionGroup;
    }

    public void attachOrderMenu(OrderMenu orderMenu){
        this.orderMenu = orderMenu;
        orderMenu.getMenuOptions().add(this);
    }
}
