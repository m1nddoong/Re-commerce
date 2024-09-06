package com.example.market.domain.used_trade.repository;

import com.example.market.domain.used_trade.entity.QTradeOffer;
import com.example.market.domain.used_trade.entity.TradeOffer;
import com.example.market.domain.used_trade.entity.TradeOffer.OfferStatus;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TradeOfferRepositoryImpl implements TradeOfferRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    // 구매 제안 등록자가 사용
    // 구매 제안 등록자가 자신이 구매 제안을 한 모든 물품을 리스트로 반환해야한다.
    // 이떄 전체 테이블에서 구매 제안 등록자의 userId 에 해당하는 컬럼 모두 조회
    @Override
    public List<TradeOffer> getTradeOfferListWithUserId(Long userId) {
        QTradeOffer tradeOffer = QTradeOffer.tradeOffer;
        // QUser user = QUser.user;
        return jpaQueryFactory.selectFrom(tradeOffer) // sql 에서의 '*' 과 동일
                .where(tradeOffer.offeringUser.id.eq(userId))
                .fetch();
    }

    // 물품 등록자가 사용
    // 물품 등록자가 등록한 물품에 대해 구매 제안을 한 모든 컬럼을 조회
    // tradeOffer 엔티티에는 물품 등록자의 id 속성이 없기 떄문 나중에
    // 물품 등록자가 여러개의 물품을 등록했다면?
    // 물품 등록자가 가진 물품들에 대해 위 메서드를 여러번 호출하면 된다.
    @Override
    public List<TradeOffer> getTradeOfferListWithTradeItemId(Long tradeItemId) {
        QTradeOffer tradeOffer = QTradeOffer.tradeOffer;
        return jpaQueryFactory.selectFrom(tradeOffer)
                .where(tradeOffer.items.id.eq(tradeItemId))
                .fetch();
    }

    // OfferStatus 가 Wailt 이거나 pproval 인 경우의 튜플 모두 검색
    @Override
    public List<TradeOffer> getTradeOfferListWithStatusNotConfirm() {
        QTradeOffer tradeOffer = QTradeOffer.tradeOffer;
        return jpaQueryFactory.selectFrom(tradeOffer)
                .where(tradeOffer.offerStatus.eq(OfferStatus.Wait).or(tradeOffer.offerStatus.eq(OfferStatus.Approval)))
                .fetch();
    }


}
