package com.sns.pojang.domain.menu.dto.response;

import com.sns.pojang.domain.menu.entity.MenuOption;
import com.sns.pojang.domain.menu.entity.MenuOptionGroup;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class MenuOptionGroupResponse {
    private Long menuId;
    private String menuName; // 메뉴명
    private Long optionGroupId;
    private String optionGroupName; // 옵션 그룹명
    private List<MenuOptionResponse> options; // 옵션 목록

    public static MenuOptionGroupResponse from(MenuOptionGroup menuOptionGroup){

        List<MenuOptionResponse> options = new ArrayList<>();

        for (MenuOption menuOption : menuOptionGroup.getMenuOptions()){
            options.add(MenuOptionResponse.from(menuOption));
        }

        return MenuOptionGroupResponse.builder()
                .menuId(menuOptionGroup.getMenu().getId())
                .menuName(menuOptionGroup.getMenu().getName())
                .optionGroupId(menuOptionGroup.getId())
                .optionGroupName(menuOptionGroup.getName())
                .options(options)
                .build();
    }
}
