package com.sns.pojang.domain.menu.repository;

import com.sns.pojang.domain.menu.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    Page<Menu> findByDeleteYnAndStoreId(String deleteYn, Long storeId, Pageable pageable);
}
