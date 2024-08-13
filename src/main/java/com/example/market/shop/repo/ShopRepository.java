package com.example.market.shop.repo;

import com.example.market.auth.entity.User;
import com.example.market.shop.entity.Shop;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long>, ShopRepositoryCustom {
    Optional<Shop> findShopByUserId(Long userId);
}
