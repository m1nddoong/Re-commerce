package com.example.market.domain.shop.service;

import com.example.market.domain.user.entity.User;
import com.example.market.global.error.exception.GlobalCustomException;
import com.example.market.global.error.exception.ErrorCode;
import com.example.market.global.auth.AuthenticationFacade;
import com.example.market.domain.shop.constant.OrderStatus;
import com.example.market.domain.shop.dto.OrderItemDto;
import com.example.market.domain.shop.dto.PurchaseRequestDto;
import com.example.market.domain.shop.entity.Order;
import com.example.market.domain.shop.entity.Shop;
import com.example.market.domain.shop.repository.ItemRepository;
import com.example.market.domain.shop.repository.OrderItemRepository;
import com.example.market.domain.shop.repository.OrderRepository;
import com.example.market.domain.shop.dto.OrderDto;
import com.example.market.domain.shop.entity.Item;
import com.example.market.domain.shop.entity.OrderItem;
import com.example.market.domain.shop.repository.ShopRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
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
    private final OrderItemRepository orderItemRepository;
    private final ShopRepository shopRepository;
    private final AuthenticationFacade authFacade;

    // 쇼핑몰 상품 구매 요청
    // 구매 요청 후 사용자는 구매에 필요한 금액을 전달한다고 가정한다.
    @Transactional
    public OrderDto createOrder(PurchaseRequestDto dto) {
        User currentUser = authFacade.extractUser();
        // '주문' 생성
        Order newOrder = orderRepository.save(Order.builder()
                .user(currentUser)
                .status(OrderStatus.ORDER_REQUEST) // 주문 요청 상태로 변환
                .build());
        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItemDto itemDto : dto.getOrderItems()) {
            // 주문할 상품을 레포지토리에서 찾고
            Item item = itemRepository.findById(itemDto.getItemId())
                    .orElseThrow(() -> new GlobalCustomException(ErrorCode.ITEM_NOT_EXISTS));
            // 주문한 상품 정보 생성하여 리스트에 저장
            OrderItem orderItem = orderItemRepository.save(OrderItem.builder()
                    .order(newOrder) // 주문과의 연관 설정
                    .item(item)
                    .quantity(itemDto.getQuantity())
                    .build());
            orderItemList.add(orderItem);
        }
        // 주문 상품 리스트를 주문에 저장
        newOrder.setItems(orderItemList);
        return OrderDto.fromEntity(orderRepository.save(newOrder));
    }

    // 상품 주문 전체 취소
    public void cancelOrder(Long orderId) {
        User currentUser = authFacade.extractUser();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.ORDER_NOT_EXISTS));
        // 주문자와 인증된 사용자가 동일한지 체크
        if (!order.getUser().getId().equals(currentUser.getId())) {
            throw new GlobalCustomException(ErrorCode.ORDER_NO_PERMISSION);
        }
        // 주문 상태 체크
        if (order.getStatus().equals(OrderStatus.ORDER_APPROVAL)) {
            throw new GlobalCustomException(ErrorCode.ORDER_ALREADY_APPROVAL);
        }
        if (order.getStatus().equals(OrderStatus.ORDER_CANCEL)) {
            throw new GlobalCustomException(ErrorCode.ORDER_ALREADY_CANCEL);
        }
        order.setStatus(OrderStatus.ORDER_CANCEL);
        orderRepository.save(order);
    }

    // 관리자의 상품 주문 수락, 재고 갱신
    @Transactional
    public void approvalOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.ORDER_NOT_EXISTS));

        // 주문 상태 확인
        if (order.getStatus().equals(OrderStatus.ORDER_CANCEL)) {
            throw new GlobalCustomException(ErrorCode.ORDER_ALREADY_CANCEL);
        }
        if (order.getStatus().equals(OrderStatus.ORDER_APPROVAL)) {
            throw new GlobalCustomException(ErrorCode.ORDER_ALREADY_APPROVAL);
        }

        // '주문' 내 '주문 상품' 정보를 가져온다.
        for (OrderItem orderItem : order.getItems()) {
            // 재고 업데이트 및 저장
            Item item  = itemRepository.findById(orderItem.getItem().getId())
                            .orElseThrow(() -> new GlobalCustomException(ErrorCode.ITEM_NOT_EXISTS));
            // 재고 확인 및 업데이트
            if (item.getStock() < orderItem.getQuantity()) {
                throw new GlobalCustomException(ErrorCode.ITEM_OUT_OF_STOCK);
            }
            item.setStock(item.getStock() - orderItem.getQuantity());
            itemRepository.save(item);
        }
        order.setStatus(OrderStatus.ORDER_APPROVAL);
        orderRepository.save(order);

        // 주문과 관련된 쇼핑몰의 마지막 거래일 업데이트
        Item item  = itemRepository.findById(order.getItems().get(0).getItem().getId())
                .orElseThrow(() -> new GlobalCustomException(ErrorCode.ITEM_NOT_EXISTS));
        Shop shop = item.getShop();
        shop.setLastTransactionDate(LocalDateTime.now());
        shopRepository.save(shop);
    }
}