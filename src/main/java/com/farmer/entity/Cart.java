package com.farmer.entity;
/* 장바구니 */
//장바구나와 회원은 일대일로 매핑돼 있으며, 장바구니 엔티티가 회원 엔티티를 참조하는 일대일 단방향 매핑입니다.
//이렇게 매핑을 하면 장바구니 엔티티를 조회하면서 회원 엔티티의 정보도 동시에 가져올 수 있는 장점이 있습니다.

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart extends BaseEntity{

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) //← @OneToONe 어노테이션을 이용하셔 엔티티와 일대일로 매핑합니다.

    //↓ @JoinColumn 어노테이션을 이용해 매핑할 외래키를 지정합니다. name 속성에는 매핑할 외래키의 이름을 설정합니다.
    //↓ @JoinColumn의 name을 명시하지 않으면 JPA가 알아서 ID를 찾지만 컬럼명이 원하는 대로 생성되지 않을 수 있습니다.
    @JoinColumn(name = "member_id")
    private Member member;

}
