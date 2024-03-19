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

public class Order {

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

    private LocalDateTime regTime;

    private LocalDateTime updateTime;



}
