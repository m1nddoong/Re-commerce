package com.example.market.trade.service;

import com.example.market.auth.entity.User;
import com.example.market.common.util.AuthenticationFacade;
import com.example.market.trade.dto.TradeOfferDto;
import com.example.market.trade.entity.TradeItem;
import com.example.market.trade.entity.TradeOffer;
import com.example.market.trade.repo.TradeItemRepository;
import com.example.market.trade.repo.TradeOfferRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



@Service
@RequiredArgsConstructor
public class TradeOfferService {
    private final TradeOfferRepository tradeOfferRepository;
    private final TradeItemRepository tradeItemRepository;
    private final AuthenticationFacade authenticationFacade;

    // 구매 제안 등록
    public TradeOfferDto requestTradeOffer(Long tradeItemId) {
        // 등록된 물품인지 아닌지 확인
        TradeItem tradeItem = tradeItemRepository.findById(tradeItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Spring Security 에 의해서 ACTIVE 사용자만 이 엔드포인트에 접근 가능하도록 할 것.
        // 따라서 구매 제안을 하는 사람이 이 물품의 주인만 아니면 비활성 사용자는 알아서 제외
        // 구매를 제안하는 사람이 물품의 주인인지 아닌지
        User user = authenticationFacade.extractUser();
        if (!tradeItem.getUser().getUuid().equals(user.getUuid())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        // 구매 제안 생성
        TradeOffer tradeOffer = new TradeOffer();
        tradeOffer.setItems(tradeItem);
        tradeOffer.setOfferingUser(user);
        tradeOfferRepository.save(tradeOffer);
        return TradeOfferDto.fromEntity(tradeOffer);
    }
}
