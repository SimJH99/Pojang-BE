package com.sns.pojang.domain.menu.dto.response;

import com.sns.pojang.domain.menu.entity.Menu;
import com.sns.pojang.domain.menu.entity.MenuOption;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MenuOptionResponse {
    private Long id;
    private String name; // 메뉴 옵션명
    private int price; // 메뉴 옵션 가격
    private String imageUrl; // 메뉴 이미지 url

    public static MenuOptionResponse from(MenuOption menuOption){

        return MenuOptionResponse.builder()
                .id(menuOption.getId())
                .name(menuOption.getName())
                .price(menuOption.getPrice())
                .build();
    }
}
