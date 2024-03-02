package com.sns.pojang.domain.order.entity;

import com.sns.pojang.domain.menu.entity.MenuOption;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMenuOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_option_id", nullable = false)
    private MenuOption menuOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_menu_id", nullable = false)
    private OrderMenu orderMenu;

    @Builder
    public OrderMenuOption(MenuOption menuOption){
        this.menuOption = menuOption;
    }

    public void attachOrderMenu(OrderMenu orderMenu){
        this.orderMenu = orderMenu;
        orderMenu.getOrderMenuOptions().add(this);
    }
}
