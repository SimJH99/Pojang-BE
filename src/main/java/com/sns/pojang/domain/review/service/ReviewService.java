package com.sns.pojang.domain.review.service;

import com.sns.pojang.domain.favorite.dto.response.CreateFavoriteResponse;
import com.sns.pojang.domain.favorite.entity.Favorite;
import com.sns.pojang.domain.favorite.exception.FavoriteDuplicateException;
import com.sns.pojang.domain.favorite.exception.FavoriteNotFoundException;
import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.exception.MemberNotFoundException;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.domain.review.dto.response.CreateReviewResponse;
import com.sns.pojang.domain.review.repository.ReviewRepository;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.domain.store.exception.StoreNotFoundException;
import com.sns.pojang.domain.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, MemberRepository memberRepository, StoreRepository storeRepository) {
        this.reviewRepository = reviewRepository;
        this.memberRepository = memberRepository;
        this.storeRepository = storeRepository;
    }

    public CreateReviewResponse createReview(Long storeId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        Favorite favorite = Favorite.builder()
                .member(member)
                .store(store)
                .build();
        return CreateFavoriteResponse.from(reviewRepository.save(favorite));
    }

}
