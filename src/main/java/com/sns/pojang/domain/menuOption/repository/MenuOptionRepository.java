package com.sns.pojang.domain.menuOption.repository;

import com.sns.pojang.domain.menuOption.entity.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {

}
