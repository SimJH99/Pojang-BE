package com.sns.pojang.domain.menu.dto.request;

import com.sns.pojang.domain.menu.entity.MenuOptionGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

// 한 매장에서 한 개의 주문만 생성 가능
@Data
public class OptionGroupRequest {
    @NotEmpty(message = "메뉴 옵션 그룹 이름은 비어있으면 안됩니다.")
    private String name; // 메뉴 옵션 그룹 이름
    private List<OptionRequest> options;

    public MenuOptionGroup toEntity(){
        return new MenuOptionGroup(name);
    }
}
