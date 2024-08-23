package com.example.market.domain.trade.repository;

import com.example.market.domain.trade.entity.TradeItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeItemRepository extends JpaRepository<TradeItem, Long> {
}
