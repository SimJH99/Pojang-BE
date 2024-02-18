package com.sns.pojang.domain.menu.dto.request;

import com.sns.pojang.domain.menu.entity.MenuOption;
import com.sns.pojang.domain.menu.entity.MenuOptionGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

// 한 매장에서 한 개의 주문만 생성 가능
@Data
public class CreateMenuOptionRequest {
    @NotEmpty(message = "메뉴 옵션 이름은 비어있으면 안됩니다.")
    private String name; // 메뉴 옵션 그룹 이름
    @NotEmpty(message = "메뉴 옵션 가격은 비어있으면 안됩니다.")
    private int price;

    public MenuOption toEntity(MenuOptionGroup menuOptionGroup){
        return MenuOption.builder()
                .name(name)
                .price(price)
                .menuOptionGroup(menuOptionGroup)
                .build();
    }
}
