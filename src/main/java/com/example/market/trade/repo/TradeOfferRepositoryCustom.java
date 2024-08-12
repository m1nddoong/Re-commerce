package com.example.market.trade.repo;

import com.example.market.trade.dto.TradeOfferDto;
import com.example.market.trade.entity.TradeOffer;
import com.example.market.trade.entity.TradeOffer.OfferStatus;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeOfferRepositoryCustom {
    List<TradeOffer> getTradeOfferListWithUserId(Long userId);

    List<TradeOffer> getTradeOfferListWithTradeItemId(Long tradeItemId);

    List<TradeOffer> getTradeOfferListWithStatusNotConfirm();
}
