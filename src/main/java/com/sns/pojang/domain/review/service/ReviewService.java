package com.sns.pojang.domain.review.service;

import com.sns.pojang.domain.favorite.dto.response.CreateFavoriteResponse;
import com.sns.pojang.domain.favorite.entity.Favorite;
import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.exception.MemberNotFoundException;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.order.repository.OrderRepository;
import com.sns.pojang.domain.review.dto.request.CreateReviewRequest;
import com.sns.pojang.domain.review.dto.response.CreateReviewResponse;
import com.sns.pojang.domain.review.entity.Review;
import com.sns.pojang.domain.review.exception.ReviewDuplicateException;
import com.sns.pojang.domain.review.repository.ReviewRepository;
import com.sns.pojang.domain.order.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    @Autowired
    public ReviewService(ReviewRepository reviewRepository, MemberRepository memberRepository, OrderRepository orderRepository) {
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
    }

    public CreateReviewResponse createReview(Long orderId, CreateReviewRequest createReviewRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        // 주문과 동일한 회원인지 체크
        if(member.getId() != order.getMember().getId()) {
            throw new AccessDeniedException("주문한 회원이 아닙니다.");
        }
        // 리뷰 존재 여부 체크
        if(reviewRepository.findByOrder(order).isPresent()) {
            throw new ReviewDuplicateException();
        }
        Review review = createReviewRequest.toEntity(order, createReviewRequest.getRating(), createReviewRequest.getContents());
        return CreateReviewResponse.from(reviewRepository.save(review));
    }

}