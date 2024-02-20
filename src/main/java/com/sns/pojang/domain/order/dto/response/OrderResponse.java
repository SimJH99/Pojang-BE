package com.sns.pojang.domain.order.dto.response;

import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.order.entity.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderResponse {
    private Long orderId; // 주문 번호
    private String customer; // 주문자 닉네임
    private String store; // 가게명
    private int totalPrice; // 총 주문 금액
    private OrderStatus orderStatus; // 주문 상태

    public static OrderResponse from(Order order){
        return OrderResponse.builder()
                .orderId(order.getId())
                .customer(order.getMember().getNickname())
                .store(order.getStore().getName())
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
