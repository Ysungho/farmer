package com.farmer.controller;
/* 상품 등록 페이지 */
import com.farmer.dto.ItemFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.farmer.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemFormDto());
        return "item/itemForm";
    }

    @PostMapping(value = "/admin/item/new")
    public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {

        if (bindingResult.hasErrors()) {
            return "item/itemForm";
        }

        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemForm";
        }

        try {
            itemService.saveItem(itemFormDto, itemImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "/admin/item/{itemId}")
    public String itemDtl(@PathVariable("itemId") Long itemId, Model model) {

        try {
            ItemFormDto itemFormDto = itemService.getItemDtl(itemId);//조회한 상품 데이터를 모델에 담아서 뷰로 전달
            model.addAttribute("itemFormDto", itemFormDto);
        } catch (EntityNotFoundException e) {//상품 엔티티가 존재하지 않을 경우 에러메시지를 담아서 상품 등록 페이지로 이동
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("itemFormDto", new ItemFormDto());
            return "item/itemForm";
        }

        return "item/itemForm";
    }
    @PostMapping(value = "/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
                             @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model) {
        if (bindingResult.hasErrors()) {//상품 등록시 필수 값이 없다면 다시 상품 등록 페이지로 변환
            return "item/itemForm";
        }

        //↓ 상품 등록 시 첫번째 이미지가 없다면 에러 메시지와 함께 상품 등록 페이지로 전환.
        //↓ 상품의 첫 번째 이미지는 메인 페이지에서 보여줄 상품 이미지로 사용하기 위해서 필수값 지정해야함
        if (itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "item/itemForm";
        }

        try {
            //↓ 상품 저장 로직을 호출. 매개 변수 로 상품 정보와 상품 이미지 정보를 담고 있는 itemImgFileList를 넘겨줌
            itemService.updateItem(itemFormDto, itemImgFileList);//상품 수정 로직을 호출
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/";//상품이 정상적으로 등록되었다면 메인 페이지로 이동
    }
}
