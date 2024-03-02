package com.sns.pojang.domain.order.dto.response;

import com.sns.pojang.domain.order.entity.OrderMenuOption;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderMenuOptionResponse {
    private Long orderMenuOptionId;
    private Long menuOptionId;
    private Long orderMenuId;
    private String optionName;
    private int optionPrice;

    public static OrderMenuOptionResponse from(OrderMenuOption orderMenuOption){
        return OrderMenuOptionResponse.builder()
                .orderMenuOptionId(orderMenuOption.getId())
                .menuOptionId(orderMenuOption.getMenuOption().getId())
                .orderMenuId(orderMenuOption.getOrderMenu().getId())
                .optionName(orderMenuOption.getMenuOption().getName())
                .optionPrice(orderMenuOption.getMenuOption().getPrice())
                .build();
    }
}
