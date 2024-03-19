package com.farmer.config;
/* 스프링 시큐리티 설정: URL에 따른 인증 및 인가*/
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.farmer.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

@Configuration
// ↓ WbSecurityConfiguredAdapter를 상속받는 클래스에 @EnableWebSecurity 어노테이션을 선언하면 SpringSecurityFilterChain이 자동으로 포함됩니다.
// ↓ WebSecurityConfigureAdapter를 상속받아서 메소드 오버라이딩을 통한 보안 설정을 커스터마이징 할 수 있습니다.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    MemberService memberService;
    @Override
    // ↓ http요청에 대한 보안을 설정합니다. 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드 등에 대한 설정을 작성합니다.
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/members/login")//← 로그인 페이지 url 설정
                .defaultSuccessUrl("/")//← 로그인 성공시 이동할 url 설정
                .usernameParameter("email")//← 로그인 시 사용할 파라미터 이름으로 email을 지정
                .failureUrl("/members/login/error")//← 로그인 실패 시 이동할 url 설정
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))//← 로그아웃 url 설정
                .logoutSuccessUrl("/")//← 로그아웃 성공 시 이동할 url 설정
        ;
    }

    @Bean
    // ↓ 비밀번호를 데이터베이스에 그대로 저장했을 경우, 데이터베이스가 해킹당하면 고객의 회원 정보가 그대로 노출됩니다.
    // ↓ 이를 해결하기 위해 BCryptPasswordEncoder의 해시 함수를 이용하여 비밀번호를 암호화하여 저장합니다.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    //↓ Spring Security에서 인증은 AuthenticationManager를 통해서 이루어지며 AuthenticationManagerBuilder가 AuthenticationManager를 생성합니다.
    //↓ userDetailService를 구현하고 있는 객체로 memberService를 지정해주며, 비밀번호 암호화를 위해서 passwordEncoder를 지정합니다.
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }
}
