package com.sns.pojang.domain.review.repository;


import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.review.entity.Review;
import com.sns.pojang.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByOrderIdAndDeleteYn(Long orderId, String deleteYn);

    Optional<Review> findByOrder(Order order);

    List<Review> findByStoreAndDeleteYn(Store store, String deleteYn);

    Optional<Review> findByOrderAndDeleteYn(Order order, String n);
}