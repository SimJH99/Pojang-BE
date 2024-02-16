package com.sns.pojang.domain.favorite.entity;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.store.entity.Store;
import com.sns.pojang.global.config.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite extends BaseTimeEntity {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 매장
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    // 찜 여부
    @Column(nullable = false)
    private String favoriteYn = "Y";

    @Builder
    public Favorite(Member member, Store store) {
        this.member = member;
        this.store = store;
    }

    // 찜 취소
    public void deleteFavorite() {
        this.favoriteYn = "N";
    }


}
