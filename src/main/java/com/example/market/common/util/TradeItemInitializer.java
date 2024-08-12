package com.example.market.common.util;

import com.example.market.auth.repo.UserRepository;
import com.example.market.trade.entity.ItemStatus;
import com.example.market.trade.entity.TradeItem;
import com.example.market.trade.repo.TradeItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class TradeItemInitializer implements ApplicationRunner {
    private final TradeItemRepository tradeItemRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        tradeItemRepository.save(TradeItem.builder()
                .title("IPhone 12 pro 중고 판매")
                .description("상태 좋습니다. 연락 주세요")
                .image(null)
                .price(500000L)
                .itemStatus(ItemStatus.ON_SALE)
                .user(userRepository.findByEmail("owner123@naver.com")
                        .orElseThrow(()
                                -> new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .build()
        );
    }
}



