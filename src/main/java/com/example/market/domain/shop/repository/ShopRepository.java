package com.example.market.domain.shop.repository;

import com.example.market.domain.shop.entity.Shop;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long>, ShopRepositoryCustom {
    Optional<Shop> findShopByUserId(Long userId);
}
