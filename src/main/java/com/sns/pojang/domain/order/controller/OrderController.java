package com.sns.pojang.domain.order.controller;

import com.sns.pojang.domain.order.dto.request.OrderRequest;
import com.sns.pojang.domain.order.dto.response.CreateOrderResponse;
import com.sns.pojang.domain.order.dto.response.OrderResponse;
import com.sns.pojang.domain.order.service.OrderService;
import com.sns.pojang.global.response.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.sns.pojang.global.response.SuccessMessage.*;

@RestController
@RequestMapping("/api/stores")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    // 주문 생성
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{storeId}/orders")
    public ResponseEntity<SuccessResponse<CreateOrderResponse>> createOrder(@PathVariable Long storeId,
            @Valid @RequestBody OrderRequest orderRequest){
        return ResponseEntity.created(URI.create(""))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_ORDER_SUCCESS.getMessage(),
                        orderService.createOrder(storeId, orderRequest)));
    }

    // 고객의 주문 취소
    @GetMapping("/{storeId}/orders/{orderId}/member-cancel")
    public ResponseEntity<SuccessResponse<OrderResponse>> cancelMemberOrder(@PathVariable Long storeId,
                                                                       @PathVariable Long orderId) {
        return ResponseEntity.ok(SuccessResponse.update(HttpStatus.OK.value(),
                CANCEL_MEMBER_ORDER_SUCCESS.getMessage(),
                orderService.cancelMemberOrder(storeId, orderId)));
    }

    // 가게의 주문 취소
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/{storeId}/orders/{orderId}/store-cancel")
    public ResponseEntity<SuccessResponse<OrderResponse>> cancelStoreOrder(@PathVariable Long storeId,
                                                                           @PathVariable Long orderId) {
        return ResponseEntity.ok(SuccessResponse.update(HttpStatus.OK.value(),
                CANCEL_STORE_ORDER_SUCCESS.getMessage(),
                orderService.cancelStoreOrder(storeId, orderId)));
    }

    // 주문 접수 (가게)
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/{storeId}/orders/{orderId}/store-accept")
    public ResponseEntity<SuccessResponse<OrderResponse>> acceptOrder(@PathVariable Long storeId,
                                                                           @PathVariable Long orderId) {
        return ResponseEntity.ok(SuccessResponse.update(HttpStatus.OK.value(),
                ACCEPT_ORDER_SUCCESS.getMessage(),
                orderService.acceptOrder(storeId, orderId)));
    }

    // 주문 확정 (가게)
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/{storeId}/orders/{orderId}/store-confirm")
    public ResponseEntity<SuccessResponse<OrderResponse>> confirmOrder(@PathVariable Long storeId,
                                                                      @PathVariable Long orderId) {
        return ResponseEntity.ok(SuccessResponse.update(HttpStatus.OK.value(),
                CONFIRM_ORDER_SUCCESS.getMessage(),
                orderService.confirmOrder(storeId, orderId)));
    }

    // 주문 상세 조회
    @GetMapping("{storeId}/orders/{orderId}")
    public ResponseEntity<SuccessResponse<OrderResponse>> getOrderDetail(@PathVariable Long storeId,
                                                                         @PathVariable Long orderId) {
        return ResponseEntity.ok(SuccessResponse.read(HttpStatus.OK.value(),
                GET_ORDER_DETAIL_SUCCESS.getMessage(),
                orderService.getOrderDetail(storeId, orderId)));
    }

    // 매장별 주문 목록 조회
    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/{id}/orders")
    // Page size default: 20, 0번 페이지부터 시작
    public ResponseEntity<SuccessResponse<List<OrderResponse>>> getStoreOrders(@PathVariable Long id, Pageable pageable){
        return ResponseEntity.ok(SuccessResponse.read(HttpStatus.OK.value(),
                GET_ORDERS_SUCCESS.getMessage(),
                orderService.getStoreOrders(id, pageable)));
    }
}
