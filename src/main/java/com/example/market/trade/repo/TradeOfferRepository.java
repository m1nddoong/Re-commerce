package com.example.market.trade.repo;

import com.example.market.trade.entity.TradeOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeOfferRepository extends JpaRepository<TradeOffer, Long>, TradeOfferRepositoryCustom{

}
