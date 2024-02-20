package com.sns.pojang.domain.order.repository;

import com.sns.pojang.domain.order.entity.Order;
import com.sns.pojang.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStore(Store store);
}
