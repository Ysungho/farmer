package com.farmer.entity;
/* 장바구니 엔티티 조회 테스트 */

import static org.junit.jupiter.api.Assertions.*;

import com.farmer.dto.MemberFormDto;
import com.farmer.repository.CartRepository;
import com.farmer.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CartTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    public Member createMember() { //← 회원 엔티티를 생성하는 메소드 생성
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void findCartAndMemberTest() {
        Member member = createMember();
        memberRepository.save(member);
        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);


        //↓ JPA는 영속성 컨텍스트에 데이터를 저장 후 트랜잭션이 끝날 때 flush()를 호출하여 데이터베이스에 반영합니다.
        //↓ 회원 엔티티와 장바구니 엔티티를 영속성 컨텍스트에 저장 후 엔티티 매니저로부터 강제로 flush()를 호출하여 데이터베이스에 반영합니다.
        em.flush();

        //↓ JPA는 영속성 컨텍스트로부터 엔티티를 조회 후 영속성 컨텍스트에 엔티티가 없을 경우 데이터베이스를 조회합니다.
        //↓ 실제 데이터베이스에서 장바구니 엔티티를 가지고 올때 회원 엔티티도 같이 가지고오는지 보기 위해서 영속성 컨텍스트릴 비워주었습니다.
        em.clear();


        //↓ 저장된 장바구니 엔티티를 조회합니다.
        //↓ cart 테이블과 member 테이블을 조인해서 가져오는 쿼리문이 실행됩니다.
        //↓ cart 엔티티를 조회하면서 member 엔티티도 동시에 가져오는 것입니다.
        Cart savedCart = cartRepository.findById(cart.getId())
                .orElseThrow(EntityNotFoundException::new);

        //↓ 처음에 저장한 memeber 엔티티의 id와 savedCart에 매핑된 member 엔티티의 id를 비교합니다.
        assertEquals(savedCart.getMember().getId(), member.getId());
    }

}