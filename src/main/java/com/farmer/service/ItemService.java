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

    //↓ 상품 데이터를 읽어오는 트랜잭션을 읽기 전용을 설정.
    //↓ 이럴 경우 JPA가 변경감지를 수행하지 않아서 성능을 향상 시킬 수 있음
    @Transactional(readOnly = true)
    public ItemFormDto getItemDtl(Long itemId) {
        //↓ 해당 상품의 이미지를 조회함
        //↓ 등록 순으로 가지고 오기 위해서 상품 이미지 아이디 오름차순으로 가지고옴
        List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for (ItemImg itemImg : itemImgList) {//조회한 ItemImg엔티티를 ItemImgDto 객체로 만들어서 리스트에 추가
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }

        //↓ 상품의 아이디를 통해 상품 엔티티를 조회, 존재하지 않을 때는 EntityNotFoundException을 발생시킴
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    public Long updateItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws Exception {
        //상품 수정
        Item item = itemRepository.findById(itemFormDto.getId())//상품 등록 화면으로부터 전달받은 상품 이이디를 이용하여 상품 엔티티를 조회
                .orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemFormDto);//상품 등록 화면으로부터 전달받은 ItemFormDto를 통해 상품 엔티티를 업데이트함
        List<Long> itemImgIds = itemFormDto.getItemImgIds();//상품 이미지 아이디 리스트를 조회

        //이미지 등록
        for (int i = 0; i < itemImgFileList.size(); i++) {
            //↓ 상품 이미지를 업데이트 하기 위해서 updateItemImg()메소드에 상품 이미지 아이디와, 상품 이미지 파일 정보를 파라미터로 전달함
            itemImgService.updateItemImg(itemImgIds.get(i),
                    itemImgFileList.get(i));
        }

        return item.getId();
    }

}
