package com.sns.pojang.domain.order.entity;

import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseTimeEntity {
    //PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //주문 상태
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusMenu statusMenu = StatusMenu.PENDING;

    //결제수단
    @Column(nullable = false)
    private String payment;

    //요청사항
    private String demand;

    //통합가격
    @Column(nullable = false)
    private int totalPrice;
}
