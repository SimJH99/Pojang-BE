package com.sns.pojang.domain.review.dto.response;

import com.sns.pojang.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Getter
@Builder
public class RatingResponse {
    private String avgRating;

    public static RatingResponse from(double avgRating) {
        DecimalFormat df = new DecimalFormat("#.#");
        return RatingResponse.builder()
                .avgRating(df.format(avgRating))
                .build();
    }
}