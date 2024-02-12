package com.sns.pojang.domain.categoryOption.repository;

import com.sns.pojang.domain.categoryOption.entity.CategoryOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryOptionRepository extends JpaRepository<CategoryOption, Long> {

}
