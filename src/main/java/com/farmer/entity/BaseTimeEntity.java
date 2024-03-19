package com.farmer.entity;
/* 등록자, 수정자를 넣지 않는 테이블에 필요한 상속 내용을 위한 코드 */

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class}) //Auditin을 적용하기 위해서 @EntityListners 어노테이션을 추가
@MappedSuperclass//공동매핑 정보가 필요할 때 사용하는 어노테이션으로 부모 클래스를 상속받는 자식 클래스에 매핑 정보만 제공합니다.
@Getter
@Setter
public abstract class BaseTimeEntity {

    @CreatedDate//엔티티가 생성되어 저장될 때 시간을 자동으로 저장합니다.
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate//엔티티의 값을 변경할 때 시간을 자동으로 저장합니다.
    private LocalDateTime updateTime;

}
