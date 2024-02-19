    package com.sns.pojang.domain.menu.dto.response;

import com.sns.pojang.domain.menu.entity.Menu;
import com.sns.pojang.domain.menu.entity.MenuOptionGroup;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateMenuOptionGroupResponse {
    private Long menuOptionGroupId;
    private String name;

    public static CreateMenuOptionGroupResponse from(MenuOptionGroup menuOptionGroup){

        return CreateMenuOptionGroupResponse.builder()
                .menuOptionGroupId(menuOptionGroup.getId())
                .name(menuOptionGroup.getName())
                .build();
    }
}
