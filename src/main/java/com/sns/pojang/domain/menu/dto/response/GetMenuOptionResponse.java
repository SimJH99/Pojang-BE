package com.sns.pojang.domain.menu.dto.response;

import com.sns.pojang.domain.menu.entity.MenuOption;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetMenuOptionResponse {
    private Long id;
    private String name; // 메뉴 옵션명
    private int price; // 옵션 가격

    public static GetMenuOptionResponse from(MenuOption menuOption){

        return GetMenuOptionResponse.builder()
                .id(menuOption.getId())
                .name(menuOption.getName())
                .price(menuOption.getPrice())
                .build();
    }
}
