package com.sns.pojang.domain.order.controller;

import com.sns.pojang.domain.order.dto.request.OrderRequest;
import com.sns.pojang.domain.order.dto.response.CreateOrderResponse;
import com.sns.pojang.domain.order.dto.response.OrderResponse;
import com.sns.pojang.domain.order.dto.response.GetOrderDetailResponse;
import com.sns.pojang.domain.order.service.OrderService;
import com.sns.pojang.global.response.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

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

    // 주문 취소
    @PatchMapping("/{storeId}/orders/{orderId}/cancel")
    public ResponseEntity<SuccessResponse<OrderResponse>> cancelOrder(@PathVariable Long storeId,
                                                                      @PathVariable Long orderId) {
        return ResponseEntity.ok(SuccessResponse.update(HttpStatus.OK.value(),
                CANCEL_ORDER_SUCCESS.getMessage(),
                orderService.cancelOrder(storeId, orderId)));
    }

    // 주문 상세 조회
    @GetMapping("{storeId}/orders/{orderId}")
    public ResponseEntity<SuccessResponse<GetOrderDetailResponse>> getOrderDetail(@PathVariable Long storeId,
                                                                                  @PathVariable Long orderId) {
        return ResponseEntity.ok(SuccessResponse.read(HttpStatus.OK.value(),
                GET_ORDER_DETAIL_SUCCESS.getMessage(),
                orderService.getOrderDetail(storeId, orderId)));
    }
}
