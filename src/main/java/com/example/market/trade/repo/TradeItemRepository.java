package com.example.market.trade.repo;

import com.example.market.trade.entity.TradeItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeItemRepository extends JpaRepository<TradeItem, Long> {
}
