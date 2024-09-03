package com.example.market.Integrationtest;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.market.domain.used_trade.dto.TradeItemDto;
import com.example.market.domain.used_trade.entity.TradeItem.ItemStatus;
import com.example.market.domain.used_trade.service.TradeItemService;
import com.example.market.util.JsonUtil;
import com.example.market.util.WithCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TradeItemControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeItemService tradeItemService;


    @Test
    @DisplayName("ACTIVE 사용자 '정형돈' - 중고 거래 물품 등록 성공")
    @WithCustomUser("123e4567-e89b-12d3-a456-426614174000") // UUID 기반의 사용자 설정
    public void addTradeItem() throws Exception {
        // given
        TradeItemDto tradeItemDto = TradeItemDto.builder()
                .title("아이폰 15")
                .description("싸게 팝니다")
                .price(500000L)
                .user("123e4567-e89b-12d3-a456-426614174000")
                .build();

        byte[] dtoJsonBytes = JsonUtil.toJson(tradeItemDto);

        MockMultipartFile dtoFile = new MockMultipartFile("dto", "dto", "application/json", dtoJsonBytes);

        TradeItemDto savedTradeItemDto = TradeItemDto.builder()
                .title("아이폰 15")
                .description("싸게 팝니다")
                .price(500000L)
                .itemStatus(ItemStatus.ON_SALE)
                .user("123e4567-e89b-12d3-a456-426614174000")
                .build();

        given(tradeItemService.createTradeItem(any(TradeItemDto.class), any(MultipartFile.class)))
                .willReturn(savedTradeItemDto);

        mockMvc.perform(multipart("/trade-item/create")
                        .file(dtoFile)
                        .contentType("multipart/form-data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("아이폰 15"));
    }

}
