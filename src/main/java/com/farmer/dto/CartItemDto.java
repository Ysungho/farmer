package com.farmer.dto;
/* 장바구니에 담을 상품의 아이디와 수량을 전달받을 클래스
*  단, 장바구니에 담길 상품의 최소 수량은 1로 제한*/

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartItemDto {

    @NotNull(message = "상품 아이디는 필수 입력 값 입니다.")
    private Long itemId;

    @Min(value = 1, message = "최소 1개 이상 담아주세요")
    private int count;

}
