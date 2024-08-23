package com.example.market.domain.shop.repository;

import com.example.market.domain.shop.dto.ItemDto;
import com.example.market.domain.shop.dto.SearchItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepositoryCustom {
    Page<ItemDto> getItemListWithPages(SearchItemDto dto, Pageable pageable);
}
