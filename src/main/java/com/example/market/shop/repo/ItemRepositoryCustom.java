package com.example.market.shop.repo;

import com.example.market.shop.dto.ItemDto;
import com.example.market.shop.dto.SearchItemDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepositoryCustom {
    Page<ItemDto> getItemListWithPages(SearchItemDto dto, Pageable pageable);
}
