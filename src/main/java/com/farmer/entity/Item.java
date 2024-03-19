/*상품을 관리하는 클래스 설정*/

package com.farmer.entity;

import com.farmer.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity//Item 클래스를 entity 선언합니다. 또한 @Table 어노테이션을 통해 어떤 테이블과 매핑될지 지정합니다.
@Table(name="item")//Item 테이블과 매핑되도록 name을 Item으로 지정합니다.
@Getter
@Setter
@ToString

public class Item {
    @Id//entity로 선언한 클래스는 반드시 기본키를 가져야 합니다. 기본키가 되는 멤버 변수에 @Id 어노테이션을 붙여줍니다.
    @Column(name="item_id")
    //테이블에 매핑되는 칼럼의 이름을 @Column 어노테이션을 통해 설정해 줍니다. item 클래스의 id 변수와 item 테이블의 item_id컬럼이 매핑되도록 합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)//@GeneratedValue 어노테이션을 통해 기본키 생성 전략을 AUTO로 지정하였습니다.
    private Long id;       //상품 코드

    //@Column 어노테이션의 nullable 속성을 이용해서 항상 값이 있어야 하는 필드는 not null로 설정합니다.
    //String 필드는 default 값으로 255가 설정돼 있습니다. 각 String 필드마다 필요한 길이를 length 속성에 default 값을 세팅합니다.
    @Column(nullable = false, length = 50)
    private String itemNm; //상품명

    @Column(name="price", nullable = false)
    private Integer price; //가격

    @Column(nullable = false)
    private int stockNumber; //재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; //상품 판매 상태

    private LocalDateTime regTime; //등록 시간

    private LocalDateTime updateTime; //수정 시간
}
