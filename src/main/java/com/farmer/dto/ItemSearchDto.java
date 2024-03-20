package com.farmer.dto;
/* 상품 데이터 조회사 상품 조회 조건을 가지고 있는 클래스 */

import com.farmer.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {

    private String searchDateType;//현재 시간과 상품 등록일을 비교해서 상품 데이터를 조회함

    private ItemSellStatus searchSellStatus;//상품의 판매 상태를 기준으로 상품 데이터를 조회

    private String searchBy;//상품을 조회할 때 어떤 유형으로 조회할지 선택


    //↓ 조회할 검색어 저장할 변수.
    //↓ searchBy가 itemNm일 경우 상품명을 기준으로 검색하고, createdBy일 경우 상품 등록자 아이디 기준으로 검색함.
    private String searchQuery = "";
}
