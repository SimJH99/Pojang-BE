package com.sns.pojang.domain.menuCategoryName.repository;

import com.sns.pojang.domain.menuCategoryName.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

}
