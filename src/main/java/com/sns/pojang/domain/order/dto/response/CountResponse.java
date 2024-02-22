package com.sns.pojang.domain.order.dto.response;

import com.sns.pojang.domain.menu.entity.MenuOption;
import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.order.entity.OrderMenu;
import com.sns.pojang.domain.store.entity.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class CountResponse {
    private Long storeId;
    private String store; // 가게명
    private int count; // 총 주문 금액

    public static CountResponse from(Store store, int count) {

        return CountResponse.builder()
                .storeId(store.getId())
                .store(store.getName())
                .count(count)
                .build();
    }
}
