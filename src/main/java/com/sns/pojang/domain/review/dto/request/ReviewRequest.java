package com.sns.pojang.domain.review.dto.request;

import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.review.entity.Review;
import com.sns.pojang.domain.store.entity.Store;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ReviewRequest {

    // 별점
    @NotNull(message = "별점을 입력해주세요.")
    private int rating;

    // 내용
    @NotEmpty(message = "리뷰를 작성해주세요.")
    private String contents;

    public Review toEntity(Order order, Store store, int rating, String contents){
        return Review.builder()
                .order(order)
                .store(store)
                .rating(rating)
                .contents(contents)
                .build();
    }

}