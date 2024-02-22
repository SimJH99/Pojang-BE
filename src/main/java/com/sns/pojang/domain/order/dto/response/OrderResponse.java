package com.sns.pojang.domain.order.dto.response;

import com.sns.pojang.domain.menu.entity.MenuOption;
import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.order.entity.OrderMenu;
import com.sns.pojang.domain.order.entity.OrderStatus;
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
@Slf4j
public class OrderResponse {
    private Long orderId; // 주문 번호
    private Long storeId;
    private String store; // 가게명
    private String customer; // 주문자 닉네임
    private String orderDateTime; // 주문 시간
    private String orderStatus; // 주문 상태
    private Map<String, Integer> orderMenuInfo; // 주문 메뉴 정보
    private Map<String, List<String>> orderMenuOptions; // 추가한 옵션 정보
    private String phoneNumber; // 주문자 연락처
    private String requirement; // 요청 사항
    private int totalPrice; // 총 주문 금액

    public static OrderResponse from(Order order) {
        String orderStatus = "";
        if(order.getOrderStatus() == OrderStatus.PENDING) {
           orderStatus = "접수대기";
        } else if (order.getOrderStatus() == OrderStatus.ORDERED) {
            orderStatus = "주문접수";
        } else if (order.getOrderStatus() == OrderStatus.CANCELED) {
            orderStatus = "주문취소";
        }else orderStatus = "픽업완료";

        Map<String, Integer> orderMenuInfo = new HashMap<>();
        Map<String, List<String>> orderMenuOptions = new HashMap<>();
        for (OrderMenu orderMenu : order.getOrderMenus()){
            orderMenuInfo.put(orderMenu.getMenu().getName(), orderMenu.getQuantity());
            List<String> menuOptions = new ArrayList<>();
            for (MenuOption menuOption : orderMenu.getMenuOptions()){
                menuOptions.add(menuOption.getName());
                log.info(menuOption.getName());
                orderMenuOptions.put(orderMenu.getMenu().getName(), menuOptions);
            }
        }
        return OrderResponse.builder()
                .orderId(order.getId())
                .storeId(order.getStore().getId())
                .store(order.getStore().getName())
                .customer(order.getMember().getNickname())
                .orderDateTime(order.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .orderStatus(orderStatus)
                .phoneNumber(order.getMember().getPhoneNumber())
                .requirement(order.getRequirement())
                .totalPrice(order.getTotalPrice())
                .orderMenuInfo(orderMenuInfo)
                .orderMenuOptions(orderMenuOptions)
                .build();
    }
}
