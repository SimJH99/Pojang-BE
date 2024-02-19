package com.sns.pojang.domain.review.dto.response;

import com.sns.pojang.domain.favorite.dto.response.CreateFavoriteResponse;
import com.sns.pojang.domain.favorite.entity.Favorite;
import com.sns.pojang.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateReviewResponse {
    private String nickname;
    private String storeName;

//    public static CreateReviewResponse from(Review review) {
//        return CreateFavoriteResponse.builder()
//                .nickname(favorite.getMember().getNickname())
//                .storeName(favorite.getStore().getName())
//                .build();
//    }
}
