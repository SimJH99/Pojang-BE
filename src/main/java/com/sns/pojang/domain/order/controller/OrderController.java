package com.sns.pojang.domain.order.controller;

import com.sns.pojang.domain.order.dto.request.OrderRequest;
import com.sns.pojang.domain.order.dto.response.CreateOrderResponse;
import com.sns.pojang.domain.order.service.OrderService;
import com.sns.pojang.global.response.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

import static com.sns.pojang.global.response.SuccessMessage.CREATE_ORDER_SUCCESS;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<CreateOrderResponse>> createOrder(
            @Valid @RequestBody OrderRequest orderRequest){
        return ResponseEntity.created(URI.create(""))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_ORDER_SUCCESS.getMessage(),
                        orderService.createOrder(orderRequest)));
    }
}
