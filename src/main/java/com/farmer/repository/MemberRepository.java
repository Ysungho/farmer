package com.farmer.repository;

// Member엔티티를 데이터베이스에 저장할 수 있는 MemeberRepository 입니다.

import com.farmer.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //↓회원 가입시 중복된 회원이 있는지 검사하기 위해서 이메일로 회원을 검사할 수 있도록 쿼리 메소드를 작성합니다.
    Member findByEmail(String email);

}