package com.farmer.service;
/* 장바구니에 상품을 담는 로직*/

import com.farmer.dto.CartItemDto;
import com.farmer.entity.Cart;
import com.farmer.entity.CartItem;
import com.farmer.entity.Item;
import com.farmer.entity.Member;
import com.farmer.repository.CartItemRepository;
import com.farmer.repository.CartRepository;
import com.farmer.repository.ItemRepository;
import com.farmer.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;



import java.util.ArrayList;
import java.util.List;

import org.thymeleaf.util.StringUtils;

import com.farmer.dto.OrderDto;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Long addCart(CartItemDto cartItemDto, String email) {

        //장바루니에 담을 상품 엔티티를 조회합니다.
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);

        //현재 로그인한 회원 엔티티를 조회합니다.
        Member member = memberRepository.findByEmail(email);

        //현재 로그인한 회원의 장바구니 엔티티를 조회합니다.
        Cart cart = cartRepository.findByMemberId(member.getId());

        //상품을 처음으로 장바구니에 담을 경우 해당 회원의 장바구니 엔티티를 생성합니다.
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        //현재 상품이 장바구니에 이미 들어가 있는지 조회합니다.
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        if (savedCartItem != null) {
            //장바구니에 이미 있던 상품일 경우 기존 수량이 현재 장바구니에 담을 수량만큼을 더해줍니다.
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        } else {
            //장바구니 엔티티, 상품 엔티티, 장바구니에 담을 수량을 이용하여 CartItem 엔티티를 생성합니다.
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());

            //장바구니에 들어갈 상품을 저장합니다.
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

}