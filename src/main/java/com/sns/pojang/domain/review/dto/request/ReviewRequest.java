package com.sns.pojang.domain.review.dto.request;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.review.entity.Review;
import com.sns.pojang.domain.store.entity.Store;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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

    // 이미지
    private MultipartFile image;

    public Review toEntity(Order order, Store store, Member member, int rating, String contents, String s3Url){
        return Review.builder()
                .order(order)
                .store(store)
                .member(member)
                .imageUrl(s3Url)
                .rating(rating)
                .contents(contents)
                .build();
    }
}