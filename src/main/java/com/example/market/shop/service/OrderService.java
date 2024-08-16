package com.example.market.shop.service;

import com.example.market.auth.entity.User;
import com.example.market.common.exception.GlobalCustomException;
import com.example.market.common.exception.GlobalErrorCode;
import com.example.market.common.util.AuthenticationFacade;
import com.example.market.shop.constant.OrderStatus;
import com.example.market.shop.dto.OrderDto;
import com.example.market.shop.dto.OrderItemDto;
import com.example.market.shop.dto.PurchaseRequestDto;
import com.example.market.shop.entity.Item;
import com.example.market.shop.entity.Order;
import com.example.market.shop.entity.OrderItem;
import com.example.market.shop.repo.ItemRepository;
import com.example.market.shop.repo.OrderRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final AuthenticationFacade authFacade;

    // 쇼핑몰 상품 구매 요청
    // 구매 요청 후 사용자는 구매에 필요한 금액을 전달한다고 가정한다.
    @Transactional
    public OrderDto purchaseRequest(PurchaseRequestDto dto) {
        User currentUser = authFacade.extractUser();

        Order newOrder = Order.builder()
                .user(currentUser)
                .status(OrderStatus.ORDER_REQUEST) // 주문 요청 상태로 변환
                .totalPrice(dto.getPayAmount())
                .build();

        // 주문한 상품 리스트 생성
        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItemDto itemDto : dto.getOrderItems()) {
            Item item = itemRepository.findById(itemDto.getItemId())
                    .orElseThrow(() -> new GlobalCustomException(GlobalErrorCode.ITEM_NOT_EXISTS));

            // 주문 항목 생성
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .quantity(itemDto.getQuantity())
                    .order(newOrder) // 주문과의 연관 설정
                    .build();

            orderItemList.add(orderItem);
        }
        // 주문정보에 주문한 상품 리스트 저장
        newOrder.setItems(orderItemList);
        return OrderDto.fromEntity(orderRepository.save(newOrder));
    }
}


