package com.example.market.domain.trade.repository;

import com.example.market.domain.trade.entity.TradeOffer;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeOfferRepositoryCustom {
    List<TradeOffer> getTradeOfferListWithUserId(Long userId);

    List<TradeOffer> getTradeOfferListWithTradeItemId(Long tradeItemId);

    List<TradeOffer> getTradeOfferListWithStatusNotConfirm();
}
