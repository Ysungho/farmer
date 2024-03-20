package com.farmer.service;
/* 주문 기능 구현이 정상적으로 작동하는지 테스트 코드 */


import static org.junit.jupiter.api.Assertions.*;

import com.farmer.constant.ItemSellStatus;
import com.farmer.constant.OrderStatus;
import com.farmer.dto.OrderDto;
import com.farmer.entity.Item;
import com.farmer.entity.Member;
import com.farmer.entity.Order;
import com.farmer.entity.OrderItem;
import com.farmer.repository.ItemRepository;
import com.farmer.repository.MemberRepository;
import com.farmer.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderServiceTest {


    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    //테스트를 위해서 주문할 상품과 회원 정보를 저장하는 메소드를 생성합니다.
    public Item saveItem() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return itemRepository.save(item);
    }

    //테스트를 위해서 주문할 상품과 회원 정보를 저장하는 메소드를 생성합니다.
    public Member saveMember() {
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);

    }

    @Test
    @DisplayName("주문 테스트")
    public void order() {
        Item item = saveItem();
        Member member = saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);//주문할 상품을 orderDto 객체에 세팅합니다
        orderDto.setItemId(item.getId());//주문할 상품 수량을 orderDto 객체에 세팅합니다.

        //주문 로지 ㄱ호출 결과 생성된 주문 번호를 orderId 변수에 저장합니다.
        Long orderId = orderService.order(orderDto, member.getEmail());

        //주문번호를 이용하여 저장된 주문 정보를 조회합니다.
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems();

        //주문한 상품의 총 가격을 구합니다.
        int totalPrice = orderDto.getCount() * item.getPrice();

        //주문한 상품의 총 가격과 데이터베이스에 저장된 상품의 가격을 비교하여 같으면 테스트가 성공적으로 종료합니다.
        assertEquals(totalPrice, order.getTotalPrice());
    }

}