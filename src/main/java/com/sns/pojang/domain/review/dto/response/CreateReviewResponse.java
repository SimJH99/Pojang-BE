package com.sns.pojang.domain.review.dto.response;

import com.sns.pojang.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateReviewResponse {
    private String nickname;
    private String storeName;
    private String contents;


    public static CreateReviewResponse from(Review review) {
        return CreateReviewResponse.builder()
                .nickname(review.getOrder().getMember().getNickname())
                .storeName(review.getOrder().getStore().getName())
                .contents(review.getContents())
                .build();
    }
}