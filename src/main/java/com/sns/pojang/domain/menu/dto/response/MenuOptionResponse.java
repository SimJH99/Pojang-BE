package com.sns.pojang.domain.menu.dto.response;

import com.sns.pojang.domain.menu.entity.MenuOption;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MenuOptionResponse {
    private Long menuId;
    private String menuName;
    private Long optionGroupId;
    private String optionGroupName;
    private Long optionId;
    private String optionName;
    private int price;

    public static MenuOptionResponse from(MenuOption menuOption){
        return MenuOptionResponse.builder()
                .menuId(menuOption.getMenuOptionGroup().getMenu().getId())
                .menuName(menuOption.getMenuOptionGroup().getMenu().getName())
                .optionGroupId(menuOption.getMenuOptionGroup().getId())
                .optionGroupName(menuOption.getMenuOptionGroup().getName())
                .optionId(menuOption.getId())
                .optionName(menuOption.getName())
                .price(menuOption.getPrice())
                .build();
    }
}
