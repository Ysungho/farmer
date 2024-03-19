package com.farmer.repository;
/* 장바구니 조회 테스트(즉시로딩) */

import com.farmer.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CartRepository extends JpaRepository<Cart, Long>{

}
