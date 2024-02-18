package com.sns.pojang.domain.order.entity;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private OrderStatus orderStatus = OrderStatus.PENDING;

    //결제수단
    @Column(nullable = false)
    private String payment;

    //요청사항
    @Column(length = 30)
    private String requirement;

    //통합가격
    @Column(nullable = false)
    private int totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Order(String payment, String requirement, Store store, Member member, int totalPrice){
        this.payment = payment;
        this.requirement = requirement;
        this.store = store;
        this.member = member;
        this.totalPrice = totalPrice;
    }

    public void updateOrderStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
    }
}
