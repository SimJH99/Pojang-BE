package com.sns.pojang.domain.order.repository;

import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndOrderStatus(Long orderId, OrderStatus orderStatus);
}
