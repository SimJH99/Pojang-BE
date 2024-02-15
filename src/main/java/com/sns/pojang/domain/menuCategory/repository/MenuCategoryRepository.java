package com.sns.pojang.domain.menuCategory.repository;

import com.sns.pojang.domain.menuCategory.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {

}
