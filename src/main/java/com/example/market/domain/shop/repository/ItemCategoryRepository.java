package com.example.market.domain.shop.repository;

import com.example.market.domain.shop.entity.ItemCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
    Optional<ItemCategory> findByName(String name);
}
