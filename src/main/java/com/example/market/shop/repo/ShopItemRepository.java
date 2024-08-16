package com.example.market.shop.repo;

import com.example.market.shop.entity.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShopItemRepository extends JpaRepository<ShopItem, Long> {
}
