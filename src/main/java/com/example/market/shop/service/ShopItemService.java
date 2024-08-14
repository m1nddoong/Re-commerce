package com.example.market.shop.service;

import com.example.market.shop.repo.ShopItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopItemService {
    private final ShopItemRepository shopItemRepository;
}
