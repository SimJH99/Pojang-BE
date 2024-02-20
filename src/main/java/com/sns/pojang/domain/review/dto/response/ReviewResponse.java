package com.sns.pojang.domain.review.dto.response;

import com.sns.pojang.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewResponse {
    private Long id;
    private String nickname;
    private String storeName;
    private int rating;
    private String contents;
    private String imageUrl;

    public static ReviewResponse from(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .nickname(review.getOrder().getMember().getNickname())
                .storeName(review.getOrder().getStore().getName())
                .rating(review.getRating())
                .contents(review.getContents())
                .imageUrl(review.getImageUrl())
                .build();
    }
}