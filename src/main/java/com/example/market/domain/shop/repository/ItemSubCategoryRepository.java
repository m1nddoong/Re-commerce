package com.example.market.domain.shop.repository;

import com.example.market.domain.shop.entity.ItemSubCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemSubCategoryRepository extends JpaRepository<ItemSubCategory, Long> {
    Optional<ItemSubCategory> findByName(String name);
}



