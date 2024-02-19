package com.sns.pojang.domain.review.controller;

import com.sns.pojang.domain.favorite.dto.response.CreateFavoriteResponse;
import com.sns.pojang.domain.review.dto.response.CreateReviewResponse;
import com.sns.pojang.domain.review.service.ReviewService;
import com.sns.pojang.global.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.sns.pojang.global.response.SuccessMessage.CREATE_FAVORITE_SUCCESS;
import static com.sns.pojang.global.response.SuccessMessage.CREATE_REVIEW_SUCCESS;

@RestController
@RequestMapping("/api/stores")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 리뷰 등록
    @PostMapping("/{storeId}/reviews")
    public ResponseEntity<SuccessResponse<CreateReviewResponse>> createReview(
            @PathVariable Long storeId) {
        return ResponseEntity.created(URI.create("/" + storeId + "/reviews"))
                .body(SuccessResponse.create(HttpStatus.CREATED.value(),
                        CREATE_REVIEW_SUCCESS.getMessage(),
                        reviewService.createReview(storeId)));
    }
}
