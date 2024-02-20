package com.sns.pojang.domain.order.repository;

import java.util.Optional;
import com.sns.pojang.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.order.entity.OrderStatus;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStore(Store store);
    Optional<Order> findByIdAndOrderStatus(Long orderId, OrderStatus orderStatus);
}
