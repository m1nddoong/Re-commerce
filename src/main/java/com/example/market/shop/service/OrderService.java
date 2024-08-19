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
import com.example.market.shop.repo.OrderItemRepository;
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
    private final OrderItemRepository orderItemRepository;
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
                    .orElseThrow(() -> new GlobalCustomException(GlobalErrorCode.ITEM_NOT_EXISTS));
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
                .orElseThrow(() -> new GlobalCustomException(GlobalErrorCode.ORDER_NOT_EXISTS));
        // 주문자와 인증된 사용자가 동일한지 체크
        if (!order.getUser().getId().equals(currentUser.getId())) {
            throw new GlobalCustomException(GlobalErrorCode.ORDER_NO_PERMISSION);
        }
        // 주문 상태 체크
        if (order.getStatus().equals(OrderStatus.ORDER_APPROVAL)) {
            throw new GlobalCustomException(GlobalErrorCode.ORDER_ALREADY_APPROVAL);
        }
        if (order.getStatus().equals(OrderStatus.ORDER_CANCEL)) {
            throw new GlobalCustomException(GlobalErrorCode.ORDER_ALREADY_CANCEL);
        }
        order.setStatus(OrderStatus.ORDER_CANCEL);
        orderRepository.save(order);
    }

    // 관리자의 상품 주문 수락, 재고 갱신
    @Transactional
    public void approvalOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new GlobalCustomException(GlobalErrorCode.ORDER_NOT_EXISTS));
        // '주문' 내 '주문 상품' 정보를 가져온다.
        for (OrderItem orderItem : order.getItems()) {
            // 재고 업데이트 및 저장
            Item item  = itemRepository.findById(orderItem.getItem().getId())
                            .orElseThrow(() -> new GlobalCustomException(GlobalErrorCode.ITEM_NOT_EXISTS));
            // 재고 확인 및 업데이트
            if (item.getStock() < orderItem.getQuantity()) {
                throw new GlobalCustomException(GlobalErrorCode.ITEM_OUT_OF_STOCK);
            }
            item.setStock(item.getStock() - orderItem.getQuantity());
            itemRepository.save(item);
        }
        order.setStatus(OrderStatus.ORDER_APPROVAL);
        orderRepository.save(order);

    }
}