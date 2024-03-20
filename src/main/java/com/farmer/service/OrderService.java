package com.farmer.service;
/* 주문 로직 */

import com.farmer.dto.OrderDto;
import com.farmer.entity.*;
import com.farmer.repository.ItemRepository;
import com.farmer.repository.MemberRepository;
import com.farmer.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.farmer.repository.ItemImgRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import org.thymeleaf.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;

    private final MemberRepository memberRepository;

    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String email) {

        Item item = itemRepository.findById(orderDto.getItemId())//주문할 상품을 조회합니다.
                .orElseThrow(EntityNotFoundException::new);

        Member member = memberRepository.findByEmail(email);//현재 로그인한 회원의 이메일 정보를 이용해서 회원 정보를 조회합니다.

        List<OrderItem> orderItemList = new ArrayList<>();

        //주문할 상품 엔티티와 주문 수량을 이용하여 주문 상품 엔티티를 생성합니다.
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);

        //회원 정보와 주문할 상품 리스트 정보를 이용하여 주문 엔티티를 생성합니다.
        Order order = Order.createOrder(member, orderItemList);

        //생성한 주문 엔티티를 저장합니다.
        orderRepository.save(order);

        return order.getId();
    }

}
