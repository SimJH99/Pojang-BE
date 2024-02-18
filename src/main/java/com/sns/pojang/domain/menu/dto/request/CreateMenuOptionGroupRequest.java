package com.sns.pojang.domain.menu.dto.request;

import com.sns.pojang.domain.menu.entity.Menu;
import com.sns.pojang.domain.menu.entity.MenuOptionGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

// 한 매장에서 한 개의 주문만 생성 가능
@Data
public class CreateMenuOptionGroupRequest {
    @NotEmpty(message = "메뉴 옵션 그룹 이름은 비어있으면 안됩니다.")
    private String name; // 메뉴 옵션 그룹 이름

    public MenuOptionGroup toEntity(Menu menu){
        return new MenuOptionGroup(name, menu);
    }
}
