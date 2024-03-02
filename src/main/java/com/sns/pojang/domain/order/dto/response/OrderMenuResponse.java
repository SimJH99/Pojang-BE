package com.sns.pojang.domain.order.dto.response;

import com.sns.pojang.domain.order.entity.OrderMenu;
import com.sns.pojang.domain.order.entity.OrderMenuOption;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class OrderMenuResponse {
    private Long orderMenuId;
    private Long menuId;
    private String menuName;
    private int quantity;
    private List<OrderMenuOptionResponse> orderMenuOptions;

    public static OrderMenuResponse from(OrderMenu orderMenu){
        List<OrderMenuOptionResponse> orderMenuOptions = new ArrayList<>();
        for (OrderMenuOption orderMenuOption : orderMenu.getOrderMenuOptions()){
            orderMenuOptions.add(OrderMenuOptionResponse.from(orderMenuOption));
        }

        return OrderMenuResponse.builder()
                .orderMenuId(orderMenu.getId())
                .menuId(orderMenu.getMenu().getId())
                .menuName(orderMenu.getMenu().getName())
                .quantity(orderMenu.getQuantity())
                .orderMenuOptions(orderMenuOptions)
                .build();
    }

}
