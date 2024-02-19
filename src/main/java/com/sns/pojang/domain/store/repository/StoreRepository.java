package com.sns.pojang.domain.store.repository;

import com.sns.pojang.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByBusinessNumber(String businessNumber);
    Page<Store> findAll(Specification<Store> specification, Pageable pageable);
}
