package com.sns.pojang.domain.order.dto.request;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.store.entity.Store;
import lombok.Data;

import java.util.List;

// 한 매장에서 한 개의 주문만 생성 가능
@Data
public class OrderRequest {
    private Long storeId; // 가게 고유 번호
    private String payment; // 결제 수단
    private String requirement; // 고객 요구 사항
    private int totalPrice;
    private List<SelectedMenuRequest> selectedMenus; // 선택된 메뉴들

    public Order toEntity(Member member, Store store){
        return Order.builder()
                .payment(payment)
                .requirement(requirement)
                .store(store)
                .totalPrice(totalPrice)
                .member(member)
                .build();
    }

}
