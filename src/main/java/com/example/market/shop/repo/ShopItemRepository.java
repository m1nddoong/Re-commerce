package com.example.market.shop.repo;

import com.example.market.shop.entity.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShopItemRepository extends JpaRepository<ShopItem, Long> {
}
