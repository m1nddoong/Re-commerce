package com.example.market.shop.repo;

import com.example.market.shop.dto.ShopDto;
import com.example.market.shop.entity.Shop;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepositoryCustom {
    List<ShopDto> getShopListWithOpenRequestStatus();

    List<ShopDto> getShopListWithCloseRequestStatus();
}
