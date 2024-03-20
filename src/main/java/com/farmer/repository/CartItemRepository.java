package com.farmer.repository;
/* 장바구니에 들어갈 상품을 저장하거나 조회하기 위한 인터페이스 */

import com.farmer.dto.CartDetailDto;
import com.farmer.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("select new com.farmer.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
            "from CartItem ci, ItemImg im " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.repimgYn = 'Y' " +
            "order by ci.regTime desc"
    )

        //카트 아이디와 상품 아이디를 이용해서 상품이 장바구니에 들어있는지 조회함.
    List<CartDetailDto> findCartDetailDtoList(Long cartId);

}
