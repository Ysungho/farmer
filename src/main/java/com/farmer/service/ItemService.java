package com.farmer.service;
/* 상품을 등록하는 클래스 */

import com.farmer.dto.ItemFormDto;
import com.farmer.entity.Item;
import com.farmer.entity.ItemImg;
import com.farmer.repository.ItemImgRepository;
import com.farmer.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import com.farmer.dto.ItemImgDto;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    private final ItemImgService itemImgService;

    private final ItemImgRepository itemImgRepository;

    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {

        //상품 등록
        Item item = itemFormDto.createItem();//상품 등록폼으로부터 입력 받은 데이터를 이용하여 item 객체를 생성
        itemRepository.save(item);//상품 데이터를 저장

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            ItemImg itemImg = new ItemImg();
            itemImg.setItem(item);

            //↓ 첫 번째 이미지일 경우 대표 상품 이미지 여부를 'Y'로 세팅.
            //↓ 나머지 상품 이미지는 'N'으로 설정
            if (i == 0)
                itemImg.setRepimgYn("Y");
            else
                itemImg.setRepimgYn("N");

            itemImgService.saveItemImg(itemImg, itemImgFileList.get(i));//상품의 이미지 정보를 저장
        }

        return item.getId();
    }
}
