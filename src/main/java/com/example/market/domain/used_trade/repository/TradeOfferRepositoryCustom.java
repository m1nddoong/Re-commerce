package com.example.market.domain.used_trade.repository;

import com.example.market.domain.used_trade.entity.TradeOffer;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeOfferRepositoryCustom {
    List<TradeOffer> getTradeOfferListWithUserId(Long userId);

    List<TradeOffer> getTradeOfferListWithTradeItemId(Long tradeItemId);

    List<TradeOffer> getTradeOfferListWithStatusNotConfirm();
}
