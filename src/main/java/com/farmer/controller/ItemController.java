package com.farmer.controller;
/* 상품 등록 페이지 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {
    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        return "item/itemForm";
    }
}
