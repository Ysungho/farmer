package com.farmer.entity;
/* 주문 엔터티 */
/* 주문 <-> 주문 상품 엔티티의 매핑 관계 정리 */

import com.farmer.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
//↓ 정렬할 때 사용하는 "order" 있기 때문에 Order 엔티티에 매핑되는 테이블로 "order"지정
@Table(name = "orders")
@Getter
@Setter

public class Order extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;//← 한명의 회원은 여러 번 주문을 할 수 있으므로 주문 엔티티 기준에서 다대일 단방향 매핑을 함

    private LocalDateTime orderDate; //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태


    //↓ 주문 상품 엔티티와 일대다 매핑을 합니다.
    //↓ 외래키(order_id)가 order_item 테이블에 있으므로 연관 관계의 주인은 OrderItem  엔티티 입니다.
    //↓ Order 엔티티가 주인이 아니므로 "mappedBy" 속성으로 연관 관계의 주인을 설정합니다.
    //↓ 속성의 값으로 "order"를 적어준 이유는 OrderItem에 있는 Order에 의해 관리된다는 의미입니다.
    //↓ 즉 연관 관계의 주인의 필드인 order를 mapperBy의 값으로 세팅했습니다.
    //↓ 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하는 CasecadeType.All 옵션을 설정하였습니다.
    //↓ 고아 객체 제거를 사용하기 위해서 @OneToMany 어노테이션에서 "orphanRemoval=true"옵션을 사용하면 됩니다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)

    //↓ 하나의 주문이 여러개의 주문 상품을 갖으므로 List 자료형을 사용해서 매핑합니다.
    private List<OrderItem> orderItems = new ArrayList<>();


    //orderItems에는 주문 상품 정보들을 담아줍니다.
    //orderItem 객체를 order 객체의 orderitems 에 추가합니다.
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);

        //Order 엔티티와 OrderItem 엔티티가 양방향 참조 관계이므로, orderItem객체도 order객체를 세팅합니다.
        orderItem.setOrder(this);
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member);//상품을 주문한 회원의 정보를 세팅합니다.

        //상품페이지에서는 1개의 상품을 주문하지만, 장바구니 페이지에서는 한 번에 여러 개의 상품을 주문할 수 있습니다.
        //따라서 여러 개의 주문 상품을 담을 수 있도록 리스트 형태로 파라미터 값을 받으며 주문 객체에 orderItem 객체를 추가합니다.
        for (OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);//주문 상태를 'ORDER'로 세팅합니다.
        order.setOrderDate(LocalDateTime.now());//현재 시간을 주문 시간으로 세팅합니다.
        return order;
    }

    public int getTotalPrice() {//총 주문 금액을 구하는 메소드 입니다.
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
