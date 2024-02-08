package com.sns.pojang.domain.menuCategoryName.repository;

import com.sns.pojang.domain.menuCategoryName.entity.MenuCategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuCategoryNameRepository extends JpaRepository<MenuCategoryName, Long> {

}
