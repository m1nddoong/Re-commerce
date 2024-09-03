package com.example.market.domain.used_trade.repository;

import com.example.market.domain.used_trade.entity.TradeOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeOfferRepository extends JpaRepository<TradeOffer, Long>, TradeOfferRepositoryCustom{

}
