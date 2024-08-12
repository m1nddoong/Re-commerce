package com.example.market.trade.repo;

import com.example.market.trade.entity.TradeOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeOfferRepository extends JpaRepository<TradeOffer, Long>, TradeOfferRepositoryCustom{

}
