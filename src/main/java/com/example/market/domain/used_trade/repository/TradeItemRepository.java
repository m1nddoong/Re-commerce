package com.example.market.domain.used_trade.repository;

import com.example.market.domain.used_trade.entity.TradeItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeItemRepository extends JpaRepository<TradeItem, Long> {
}
