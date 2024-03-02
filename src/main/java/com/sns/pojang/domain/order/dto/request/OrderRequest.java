package com.sns.pojang.domain.order.dto.request;

import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.store.entity.Store;
import lombok.Data;

import java.util.List;

// 한 매장에서 한 개의 주문만 생성 가능
@Data
public class OrderRequest {
    private String payment; // 결제 수단
    private String requirement; // 고객 요구 사항
    private int totalPrice; // 총 주문 금액
    private List<SelectedMenuRequest> selectedMenus; // 선택된 메뉴들

    public Order toEntity(Store store){
        return Order.builder()
                .payment(payment)
                .requirement(requirement)
                .store(store)
                .totalPrice(totalPrice)
                .build();
    }
}
