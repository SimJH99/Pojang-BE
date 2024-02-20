package com.sns.pojang.domain.menu.dto.response;

import com.sns.pojang.domain.menu.entity.MenuOption;
import com.sns.pojang.domain.menu.entity.MenuOptionGroup;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class GetMenuOptionGroupResponse {
    private Long id;
    private String name; // 메뉴 옵션 그룹명
    private List<GetMenuOptionResponse> menuOptions; // 메뉴 옵션 List Dto

    public static GetMenuOptionGroupResponse from(MenuOptionGroup menuOptionGroup){
        List<GetMenuOptionResponse> menuOptions = new ArrayList<>();
        for (MenuOption menuOption : menuOptionGroup.getMenuOptions()){
            GetMenuOptionResponse response = GetMenuOptionResponse.from(menuOption);
            menuOptions.add(response);
        }
        return GetMenuOptionGroupResponse.builder()
                .id(menuOptionGroup.getId())
                .name(menuOptionGroup.getName())
                .menuOptions(menuOptions)
                .build();
    }
}
