package com.sns.pojang.domain.store.repository;

import com.sns.pojang.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByBusinessNumber(String businessNumber);

    Optional<Store> findByMemberId(Long memberId);
    List<Store> findAllByMemberId(Long id);
}
