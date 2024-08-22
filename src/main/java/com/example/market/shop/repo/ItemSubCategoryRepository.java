package com.example.market.shop.repo;

import com.example.market.shop.entity.ItemSubCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ItemSubCategoryRepository extends JpaRepository<ItemSubCategory, Long> {
    Optional<ItemSubCategory> findByName(String name);
}



