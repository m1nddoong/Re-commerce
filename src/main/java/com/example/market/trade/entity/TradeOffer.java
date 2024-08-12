package com.example.market.trade.entity;


import com.example.market.auth.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeOffer { // 구매 제안 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // offerId

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private TradeItem items; // 어떤 물품인지

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offering_user_id")
    private User offeringUser; // 제안하는 사람이 누구인지


    // 구매 제안의 상태 (거절 상태, 수락 상태, 확정 상태)
    @Setter
    // @Builder.Default
    @Enumerated(EnumType.STRING)
    private OfferStatus offerStatus;
    public enum OfferStatus {
        // 승인, 거절, 확정
        Approval, Rejection, Confirm
    }

}
