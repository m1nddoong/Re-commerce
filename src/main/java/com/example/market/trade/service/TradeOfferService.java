package com.example.market.trade.service;

import com.example.market.auth.entity.User;
import com.example.market.common.util.AuthenticationFacade;
import com.example.market.trade.dto.TradeOfferDto;
import com.example.market.trade.entity.TradeItem;
import com.example.market.trade.entity.TradeOffer;
import com.example.market.trade.repo.TradeItemRepository;
import com.example.market.trade.repo.TradeOfferRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
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
        log.info("찾은 물품 : {}", tradeItem.getTitle());

        // Spring Security 에 의해서 ACTIVE 사용자만 이 엔드포인트에 접근 가능하도록 할 것.
        // 따라서 구매 제안을 하는 사람이 이 물품의 주인만 아니면 비활성 사용자는 알아서 제외
        // 구매를 제안하는 사람이 물품의 주인인지 아닌지
        User user = authenticationFacade.extractUser();
        if (!tradeItem.getUser().getUuid().equals(user.getUuid())) {
            // 구매 제안 생성
            TradeOffer tradeOffer = new TradeOffer();
            tradeOffer.setItems(tradeItem);
            tradeOffer.setOfferingUser(user);
            tradeOfferRepository.save(tradeOffer);
            return TradeOfferDto.fromEntity(tradeOffer);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    // 구매 제안 확인 (제안 등록자, 물품 등록자만)
    public List<TradeOfferDto> getTradeOfferList(Long tradeItemId) {
        TradeItem tradeItem = tradeItemRepository.findById(tradeItemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        User user = authenticationFacade.extractUser();

        // 물품의 소유자와 현재 인증된 사용자 비교
        // 1. 해당 물품 등록자의 경우, 특정 tradeItemId 로 조회
        List<TradeOfferDto> response;
        if (tradeItem.getUser().getId().equals(user.getId())) {
            response = tradeOfferRepository.getTradeOfferListWithTradeItemId(tradeItemId).stream()
                    .map(m ->
                            TradeOfferDto.builder()
                                    .itemId(String.valueOf(m.getItems().getId()))
                                    .userId(String.valueOf(m.getOfferingUser().getId()))
                                    .offerStatus(String.valueOf(m.getOfferStatus()))
                                    .build())
                    .collect(Collectors.toList());
        }
        // 2. 제안 등록자의 경우, 등족자의 userId 로 조회
        else {
            response = tradeOfferRepository.getTradeOfferListWithUserId(user.getId()).stream()
                    .map(m ->
                            TradeOfferDto.builder()
                                    .itemId(String.valueOf(m.getItems().getId()))
                                    .userId(String.valueOf(m.getOfferingUser().getId()))
                                    .offerStatus(String.valueOf(m.getOfferStatus()))
                                    .build())
                    .collect(Collectors.toList());
        }
        return response;
    }
}
