/* 뷰 영역에서 사용할 ItemDto 클래스 */
/* 데이터를 주고 받을 때 Entity 클래스 자체를 반환하면 안되고 데이터 전달용 객체를 생성해서 사용해야 한다. */

package com.farmer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String itemNm;
    private Integer price;
    private String itemDetail;
    private String sellStatCd;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

}

//ItemDto 객체를 하나 생성 후 모델에 데이터를 담아서 뷰에 전달합니다.

/*
### 주의사항###
데이터를 주고 받을 때 Entity 클래스 자체를 반환하면 안 되고 데이터 전달용 객체(Data Transfer Object)르 생성해서 사용해야 합니다.
데이터 베이스의 설계를 외부에 노출할 필요도 없으며 요청과 응답 객체가 항상 엔티티와 같지 않기 때문입니다.
*/