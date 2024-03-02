package com.sns.pojang.domain.menu.dto.response;

import com.sns.pojang.domain.menu.entity.MenuOption;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MenuOptionInfoResponse {
    private Long id;
    private String name;
    private int price;

    public static MenuOptionInfoResponse from(MenuOption menuOption){
        return MenuOptionInfoResponse.builder()
                .id(menuOption.getId())
                .name(menuOption.getName())
                .price(menuOption.getPrice())
                .build();
    }
}
