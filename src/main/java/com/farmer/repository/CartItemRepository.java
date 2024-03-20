package com.farmer.repository;
/* 장바구니에 들어갈 상품을 저장하거나 조회하기 위한 인터페이스 */

import com.farmer.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

}
