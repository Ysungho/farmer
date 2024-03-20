package com.farmer.repository;
/* 사용자 정의 인터페이스 */

import com.farmer.dto.ItemSearchDto;
import com.farmer.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.farmer.dto.MainItemDto;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    //↓ 상품 조회 조건을 담고 있는 itemSearchDto 객체와 페이징 정보를 담고 있는 pageable 객체를 파라미터로 받는 getAdminItemPage 메소드를 정의
    //↓ 반환 데이터로 Page(item) 객체를 반환함.
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}
