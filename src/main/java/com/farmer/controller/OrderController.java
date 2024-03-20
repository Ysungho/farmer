package com.farmer.controller;
/* 주문 관련 요청 처리 */

import com.farmer.dto.OrderDto;
import com.farmer.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/order")
    //스프링에서 비동기 처리를 할 때 @RequestBody와 @ResponseBody 어노테이션을 사용합니다.
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto
            , BindingResult bindingResult, Principal principal) {

        //주문 정보를 받는 orderDto 객체에 데이터 바인딩 시 에러가 있는지 검사합니다.
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            //에러 정보를 ResponseEntity 객체에 담아서 반환합니다.
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        //현재 로그인 유저의 정보를 얻기 위해서 @Controller 어노테이션이 선언된 클래스에서 메소드 인자로 principal객체를 넘겨 줄 경우 해당 객체에 직접 접근할 수 있습니다.
        //principal 객체에서 현재 로그인한 회원의 이메일 정보를 조회합니다.
        String email = principal.getName();
        Long orderId;

        try {
            //화면으로부터 넘어오는 주문 정보와 회원의 이메일 정보를 이용하여 주문 로직을 호출합니다.
            orderId = orderService.order(orderDto, email);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        //결과값으로 생성된 주문 번호와 요청이 성공했다는 HTTP 응답 상태 코드를 반환합니다.
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

}
