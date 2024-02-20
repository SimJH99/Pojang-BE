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
public class GetOrderDetailResponse {
    private Long orderId; // 주문 번호
    private String store; // 가게명
    private String customer; // 주문자 닉네임
    private String orderDateTime; // 주문 시간
    private OrderStatus orderStatus; // 주문 상태
    private Map<String, Integer> orderMenuInfo; // 주문 메뉴 정보
    private Map<String, List<String>> orderMenuOptions; // 추가한 옵션 정보
    private String phoneNumber; // 주문자 연락처
    private String requirement; // 요청 사항
    private int totalPrice; // 총 주문 금액

    public static GetOrderDetailResponse from(Order order) {
        Map<String, Integer> orderMenuInfo = new HashMap<>();
        Map<String, List<String>> orderMenuOptions = new HashMap<>();
        for (OrderMenu orderMenu : order.getOrderMenus()){
            orderMenuInfo.put(orderMenu.getMenu().getName(), orderMenu.getQuantity());
            List<String> menuOptions = new ArrayList<>();
            log.info("2번째 for문 진입 전");
            for (MenuOption menuOption : orderMenu.getMenuOptions()){
                menuOptions.add(menuOption.getName());
                log.info(menuOption.getName());
                orderMenuOptions.put(orderMenu.getMenu().getName(), menuOptions);
            }
        }
        log.info(orderMenuOptions.toString());
        return GetOrderDetailResponse.builder()
                .orderId(order.getId())
                .store(order.getStore().getName())
                .customer(order.getMember().getNickname())
                .orderDateTime(order.getCreatedTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .orderStatus(order.getOrderStatus())
                .phoneNumber(order.getMember().getPhoneNumber())
                .requirement(order.getRequirement())
                .totalPrice(order.getTotalPrice())
                .orderMenuInfo(orderMenuInfo)
                .orderMenuOptions(orderMenuOptions)
                .build();
    }
}
