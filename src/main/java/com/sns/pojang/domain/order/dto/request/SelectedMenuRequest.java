package com.sns.pojang.domain.order.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class  SelectedMenuRequest {
    private Long menuId; // 메뉴 고유 번호
    private int quantity; // 선택한 메뉴 수량
    private List<SelectedOptionRequest> selectedMenuOptions; // 선택된 메뉴 옵션들
}
