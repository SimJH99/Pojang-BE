package com.sns.pojang.domain.menu.dto.response;

import com.sns.pojang.domain.menu.entity.Menu;
import com.sns.pojang.domain.menu.entity.MenuOptionGroup;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class MenuResponse {
    private Long storeId;
    private String storeName;
    private Long menuId;
    private String menuName;
    private String info; // 메뉴 정보
    private int price; // 메뉴 가격
    private String imageUrl; // 메뉴 이미지 url
    private List<MenuOptionGroupResponse> optionGroups;

    public static MenuResponse from(Menu menu, String s3Url){

        List<MenuOptionGroupResponse> optionGroups = new ArrayList<>();

        for (MenuOptionGroup menuOptionGroup : menu.getMenuOptionGroups()) {
            optionGroups.add(MenuOptionGroupResponse.from(menuOptionGroup));
        }

        return MenuResponse.builder()
                .storeId(menu.getStore().getId())
                .storeName(menu.getStore().getName())
                .menuId(menu.getId())
                .menuName(menu.getName())
                .info(menu.getMenuInfo())
                .price(menu.getPrice())
                .imageUrl(s3Url)
                .optionGroups(optionGroups)
                .build();
    }
}
