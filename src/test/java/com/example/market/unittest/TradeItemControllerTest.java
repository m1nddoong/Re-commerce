package com.example.market.unittest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.market.auth.controller.UserController;
import com.example.market.auth.service.UserService;
import com.example.market.trade.controller.TradeItemController;
import com.example.market.trade.dto.TradeItemDto;
import com.example.market.trade.entity.ItemStatus;
import com.example.market.trade.service.TradeItemService;
import com.example.market.util.JsonUtil;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class TradeItemControllerTest {
    @Mock
    private TradeItemService tradeItemService;

    @InjectMocks
    private TradeItemController tradeItemController;
    private MockMvc mockMvc;

    // 이 테스트 클래스의 개별 클레스 전에 실행할 코드
    @BeforeEach
    public void beforeEach() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(tradeItemController)
                .build();
    }


    @Test
    // 물품 등록
    @DisplayName("ACTIVE 사용자 Alex - 중고 거래 물품 성공")
    @WithCustomUserDetails(
            username = "Alex",
            password = "password",
            authorities = {"ACTIVE"}
    )
    public void addTradeItem() throws Exception {
        TradeItemDto tradeItemDto = TradeItemDto.builder()
                .title("아이폰 15")
                .description("싸게 팝니다")
                .image(null)
                .price(500000L)
                .itemStatus(ItemStatus.ON_SALE)
                .user("Alex")
                .build();

        byte[] dtoJsonBytes = JsonUtil.toJson(tradeItemDto);

        MockMultipartFile dtoFile = new MockMultipartFile("dto", "", "application/json", dtoJsonBytes);
        MockMultipartFile imgFile = new MockMultipartFile("img", "image.jpg", "image/jped",
                "image content" .getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(multipart("/trade-item/create")
                        .file(dtoFile)
                        .file(imgFile)
                        .contentType("multipart/form-data"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.title").value("아이폰 15"))
                .andExpect(jsonPath("$.description").value("싸게 팝니다"))
                .andExpect(jsonPath("$.price").value(500000L))
                .andExpect(jsonPath("$.itemStatus").value("ON_SALE"));
    }
}

