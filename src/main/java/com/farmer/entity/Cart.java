package com.farmer.entity;
/* 장바구니 */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne

    //↓ @JoinColumn 어노테이션을 이용해 매핑할 외래키를 지정합니다. name 속성에는 매핑할 외래키의 이름을 설정합니다.
    //↓ @JoinColumn의 name을 명시하지 않으면 JPA가 알아서 ID를 찾지만 컬럼명이 원하는 대로 생성되지 않을 수 있습니다.
    @JoinColumn(name = "member_id")
    private Member member;

}
