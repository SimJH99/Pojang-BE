package com.sns.pojang.domain.menu.dto.response;

import com.sns.pojang.domain.menu.entity.Menu;
import com.sns.pojang.domain.menu.entity.MenuOptionGroup;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class MenuDetailResponse {
    private String name; // 메뉴명
    private String menuInfo; // 메뉴 정보
    private int price; // 메뉴 가격
    private List<GetMenuOptionGroupResponse> menuOptionGroups; // 메뉴 옵션 그룹 List Dto

    public static MenuDetailResponse from(Menu menu){
        List<GetMenuOptionGroupResponse> menuOptionGroups = new ArrayList<>();
        for (MenuOptionGroup menuOptionGroup : menu.getMenuOptionGroups()){
            GetMenuOptionGroupResponse response = GetMenuOptionGroupResponse.from(menuOptionGroup);
            menuOptionGroups.add(response);
        }
        return MenuDetailResponse.builder()
                .name(menu.getName())
                .menuInfo(menu.getMenuInfo())
                .price(menu.getPrice())
                .menuOptionGroups(menuOptionGroups)
                .build();
    }
}
