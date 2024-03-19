package com.farmer.controller;
/* 회원가입 이후 메인 페이지로 갈 수 있는 코드 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping(value = "/")
    public String main(){
        return "main";
    }
}
