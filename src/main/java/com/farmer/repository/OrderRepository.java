package com.farmer.repository;
/* 주문 영석성 전이 테스트*/

import com.farmer.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>{
}
