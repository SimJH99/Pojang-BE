package com.sns.pojang.domain.store.repository;

import com.sns.pojang.domain.store.entity.BusinessNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessNumberRepository extends JpaRepository<BusinessNumber, Long> {
    Optional<BusinessNumber> findByBusinessNumber(String businessNumber);
}
