package com.sns.pojang.domain.categoryOptionName.repository;

import com.sns.pojang.domain.categoryOptionName.entity.CategoryOptionName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryOptionNameRepository extends JpaRepository<CategoryOptionName, Long> {

}
