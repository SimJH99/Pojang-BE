package com.sns.pojang.domain.favorite.repository;

import com.sns.pojang.domain.favorite.entity.Favorite;
import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByMemberAndStore(Member member, Store store);
}
