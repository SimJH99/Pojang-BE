package com.sns.pojang.domain.favorite.service;

import com.sns.pojang.domain.favorite.dto.response.CountFavoriteResponse;
import com.sns.pojang.domain.favorite.dto.response.CreateFavoriteResponse;
import com.sns.pojang.domain.favorite.entity.Favorite;
import com.sns.pojang.domain.favorite.exception.FavoriteDuplicateException;
import com.sns.pojang.domain.favorite.exception.FavoriteNotFoundException;
import com.sns.pojang.domain.favorite.repository.FavoriteRepository;
import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.member.exception.EmailDuplicateException;
import com.sns.pojang.domain.member.exception.MemberNotFoundException;
import com.sns.pojang.domain.member.repository.MemberRepository;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.domain.store.exception.StoreNotFoundException;
import com.sns.pojang.domain.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.sns.pojang.global.error.ErrorCode.FAVORITE_NOT_FOUND;


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
        if(favoriteRepository.findByMemberAndStore(member, store).isPresent()) {
            Favorite favorite = favoriteRepository.findByMemberAndStore(member, store).orElseThrow(FavoriteNotFoundException::new);
            if(favorite.getFavoriteYn().equals("Y")) {
                throw new FavoriteDuplicateException();
            }
            else { // 데이터는 있으나 favoriteY가 아닌 경우 Y로 변경
                favorite.updateFavorite();
                return CreateFavoriteResponse.from(favorite);
            }
        }
        Favorite favorite = Favorite.builder()
                .member(member)
                .store(store)
                .build();
        return CreateFavoriteResponse.from(favoriteRepository.save(favorite));
    }

    public void cancelFavorite(Long storeId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        Favorite favorite = favoriteRepository.findByMemberAndStore(member, store).orElseThrow(FavoriteNotFoundException::new);
        favorite.cancelFavorite();
    }

    public CountFavoriteResponse countFavorite(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(StoreNotFoundException::new);
        if(store.getDeleteYn().equals("Y")) {
            throw new StoreNotFoundException();
        }
        List<Favorite> favorites = favoriteRepository.findByStoreAndFavoriteYn(store, "Y");
        int count = 0;
        for(Favorite favorite : favorites) {
            // 회원 탈퇴 후 30일 전까지 회원 정보가 삭제되지 않으므로 걸러줘야 함
            if(favorite.getMember().getDeleteYn().equals("N")) {
                count++;
            }
        }
        return CountFavoriteResponse.from(count, store);
    }
}
