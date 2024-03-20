package com.farmer.repository;
/* 상품 이미지 정보를 저장하는 repository */

import com.farmer.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long>{

    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

    ItemImg findByItemIdAndRepimgYn(Long itemId, String repimgYn);

}
