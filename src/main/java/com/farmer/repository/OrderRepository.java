package com.farmer.repository;
/* 주문 영석성 전이 테스트*/

import com.farmer.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>{


    @Query("select o from Order o " +
            "where o.member.email = :email " +
            "order by o.orderDate desc"
    )
        //현재 로그인한 사용자의 주문 데이터를 페이징 조건에 맞춰서 조회합니다.
    List<Order> findOrders(@Param("email") String email, Pageable pageable);

    @Query("select count(o) from Order o " +
            "where o.member.email = :email"
    )
        //현재 로그인한 회원의 주문 개수가 몇개인지 조회합니다.
    Long countOrder(@Param("email") String email);

}
