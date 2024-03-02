package com.sns.pojang.domain.order.dto.request;

import lombok.Data;

@Data
public class SelectedOptionRequest {
    private Long id;
    private String name;
    private int price;
}
