package com.sns.pojang.domain.order.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SelectedMenuOptionRequest {
    private Long menuOptionGroupId; // 메뉴 옵션 그룹
    private List<Long> selectedMenuOptions; // 선택된 메뉴 옵션들
}
