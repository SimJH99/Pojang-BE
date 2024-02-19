package com.sns.pojang.domain.review.entity;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 매장
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

//    // 주문
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id", nullable = false)
//    private Order order;

    // 별점
    @Column(nullable = false)
    private int rating;

    // 내용
    @Column(length = 300, nullable = false)
    private String contents;

    @Builder
    public Review(Member member, Store store, int rating, String contents) {
        this.member = member;
        this. store = store;
        this.rating = rating;
        this. contents = contents;
    }


}
