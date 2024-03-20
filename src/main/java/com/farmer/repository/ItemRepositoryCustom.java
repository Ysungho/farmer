package com.farmer.repository;
/* 사용자 정의 인터페이스 */

import com.farmer.dto.ItemSearchDto;
import com.farmer.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}
