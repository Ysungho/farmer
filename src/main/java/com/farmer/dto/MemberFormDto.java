package com.farmer.dto;
/* 회원 가입 화면으로 부터 넘어오는 가입정보를 담는 Dto */
//유효성을 검증할 클래스
//회원 가입이 성공하면 메인 페이지로 리다이렉트
// 회원 정보 검증 및 중복 회원 가입 조건에 의해 실패한다면 다시 회원 가입페이지로 돌아가 실패이유 출력

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberFormDto {
    private String name;
    private String email;
    private String password;
    private String address;
}
