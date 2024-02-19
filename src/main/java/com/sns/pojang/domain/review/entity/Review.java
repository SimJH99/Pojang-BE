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

    // 주문
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // 별점
    @Column(nullable = false)
    private int rating;

//    //메뉴 이미지 경로
//    private String imageUrl;

    // 내용
    @Column(length = 300, nullable = false)
    private String contents;

    // 삭제여부
    @Column(nullable = false)
    private String deleteYn = "N";

    @Builder
    public Review(Order order, int rating, String contents) {
        this.order = order;
        this.rating = rating;
        this. contents = contents;
    }


}