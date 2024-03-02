package com.sns.pojang.domain.menu.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class FindMenuOptionsRequest {
    private List<Long> optionIds;
}
