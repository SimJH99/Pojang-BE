package com.sns.pojang.domain.favorite.service;

import com.sns.pojang.domain.favorite.dto.response.CreateFavoriteResponse;
import com.sns.pojang.domain.favorite.entity.Favorite;
import com.sns.pojang.domain.favorite.repository.FavoriteRepository;
import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.exception.MemberNotFoundException;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.domain.store.exception.StoreNotFoundException;
import com.sns.pojang.domain.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository, StoreRepository storeRepository, MemberRepository memberRepository) {
        this.favoriteRepository = favoriteRepository;
        this.storeRepository = storeRepository;
        this.memberRepository = memberRepository;
    }

    public CreateFavoriteResponse createFavorite(Long storeId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        Favorite favorite = Favorite.builder()
                .member(member)
                .store(store)
                .build();
        return CreateFavoriteResponse.from(favoriteRepository.save(favorite));
    }

    public void deleteFavorite(Long storeId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        Favorite favorite = favoriteRepository.findByMemberAndStore(member, store).orElseThrow();
        favorite.deleteFavorite();
    }
}
