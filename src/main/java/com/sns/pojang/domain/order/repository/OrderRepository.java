package com.sns.pojang.domain.order.repository;

import com.sns.pojang.domain.member.entity.Member;
import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.order.entity.OrderStatus;
import com.sns.pojang.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByStoreOrderByCreatedTimeDesc(Store store, Pageable pageable);
    Page<Order> findByMemberOrderByCreatedTimeDesc(Member member, Pageable pageable);
    List<Order> findByStoreOrderByCreatedTimeDesc(Store store);
    Optional<Order> findByIdAndOrderStatus(Long orderId, OrderStatus orderStatus);

    List<Order> findByStoreAndOrderStatus(Store store, OrderStatus orderStatus);
}
