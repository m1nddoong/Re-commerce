package com.example.market.domain.trade.repository;

import com.example.market.domain.trade.entity.TradeOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeOfferRepository extends JpaRepository<TradeOffer, Long>, TradeOfferRepositoryCustom{

}
