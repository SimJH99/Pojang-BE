package com.sns.pojang.domain.review.service;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.exception.MemberNotFoundException;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.order.entity.OrderStatus;
import com.sns.pojang.domain.order.exception.OrderNotConfirmException;
import com.sns.pojang.domain.order.exception.OrderNotFoundException;
import com.sns.pojang.domain.order.repository.OrderRepository;
import com.sns.pojang.domain.review.dto.request.ReviewRequest;
import com.sns.pojang.domain.review.dto.response.ReviewResponse;
import com.sns.pojang.domain.review.entity.Review;
import com.sns.pojang.domain.review.exception.ReviewDuplicateException;
import com.sns.pojang.domain.review.exception.ReviewNotFoundException;
import com.sns.pojang.domain.review.repository.ReviewRepository;
import com.sns.pojang.global.config.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private static final String FILE_TYPE = "reviews";

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final S3Service s3Service;

    public ReviewResponse createReview(Long orderId, ReviewRequest reviewRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        // 주문상태가 CONFIRM인 경우만
        Order order = orderRepository.findByIdAndOrderStatus(orderId, OrderStatus.CONFIRM).orElseThrow(OrderNotConfirmException::new);
        // 주문과 동일한 회원인지 체크
        if(member.getId() != order.getMember().getId()) {
            throw new AccessDeniedException("주문한 회원이 아닙니다.");
        }

        String imagePath = null;
        if (reviewRequest.getImage() != null && !reviewRequest.getImage().isEmpty()){
            imagePath = s3Service.uploadFile(FILE_TYPE, reviewRequest.getImage());
        }

        // 리뷰 존재 여부 체크
        if(reviewRepository.findByOrder(order).isPresent()) {
            Review review = reviewRepository.findByOrder(order).orElseThrow(ReviewNotFoundException::new);
            if(review.getDeleteYn().equals("N")) {
                throw new ReviewDuplicateException();
            }else { // 데이터는 있으나 deleteY인 경우 N로 변경
                review.updateReview(reviewRequest.getRating(),reviewRequest.getContents(), imagePath);
                return ReviewResponse.from(review, review.getImageUrl());
            }
        }

        Review review = reviewRequest.toEntity(order, order.getStore(), order.getMember(),
                reviewRequest.getRating(), reviewRequest.getContents(), imagePath);
        return ReviewResponse.from(reviewRepository.save(review), imagePath);
    }

    public ReviewResponse updateReview(Long orderId, ReviewRequest reviewRequest) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Order order = orderRepository.findByIdAndOrderStatus(orderId, OrderStatus.CONFIRM).orElseThrow(OrderNotConfirmException::new);
        if(member.getId() != order.getMember().getId()) {
            throw new AccessDeniedException("주문한 회원이 아닙니다.");
        }
        Review review = reviewRepository.findByOrderAndDeleteYn(order, "N").orElseThrow(ReviewNotFoundException::new);

        String imagePath = null;
        if (reviewRequest.getImage() != null && !reviewRequest.getImage().isEmpty()){
            imagePath = s3Service.uploadFile(FILE_TYPE, reviewRequest.getImage());
        }

        review.updateReview(reviewRequest.getRating(), reviewRequest.getContents(), imagePath);
        return ReviewResponse.from(review, imagePath);
    }

    public void deleteReview(Long orderId) {
        Review review = reviewRepository.findByOrderIdAndDeleteYn(orderId, "N").orElseThrow(ReviewNotFoundException::new);
        review.delete();
    }

    public Resource findImage(Long reviewId) {
        Review review = reviewRepository.findByIdAndDeleteYn(reviewId, "N").orElseThrow(ReviewNotFoundException::new);
        String imagePath = review.getImageUrl();
        Path path = Paths.get(imagePath);
        Resource resource;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Url Form Is Not Valid");
        }
        return resource;
    }

    public Boolean checkReview(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        return reviewRepository.findByOrder(order).isPresent();
    }
}