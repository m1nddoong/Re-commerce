package com.example.market.domain.shop.repository;

import com.example.market.domain.shop.entity.Category;
import com.example.market.domain.shop.entity.SubCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    Optional<SubCategory> findByNameAndCategory(String subCategoryName, Category category);
    Optional<SubCategory> findByName(String name);
    List<SubCategory> findByCategoryId(Long categoryId);
}



