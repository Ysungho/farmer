package com.farmer.controller;
/* 상품 등록 페이지 */
import com.farmer.dto.ItemFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;

import com.farmer.dto.ItemSearchDto;
import com.farmer.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    //↓ value에 상품 관리 화면 진입시 url에 페이지 번호가 없는 경우와 페이지 번호가 있는 경우 2가지를 매핑.
    @GetMapping(value = {"/admin/items", "/admin/items/{page}"})
    public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {

        //↓ 페이징을 위해서 PageRequest.of 메소드를 통해 Pageable 객체를 생성함
        //↓ 첫 번 째 파라미터로는 조회할 때 페이지 번호, 두 번째 파라미터로는 한 번에 가고 올 데이터 수를 넣어줌
        //↓ url 경로에 페이지 번호가 있으면 해당 페이지를 조회하도록 세팅하고, 페이지 번호가 없으면 0페이지를 조회하도록 함.
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);

        //↓조회 조건과 페이징 정보를 파라미터로 넘겨소 Page(item) 객체를 반환 받도록 함.
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

        //↓조회한 상품 데이터 및 페이징 정보에 뷰를 전달함
        model.addAttribute("items", items);

        //↓ 페이지 전환 시 기존 검색 조건을 유지한 채 이동할 수 있도록 뷰에 다시 전달함.
        model.addAttribute("itemSearchDto", itemSearchDto);

        //↓상품 관리 메뉴 하단에 보여줄 페이지 번호의 최대 개수임.
        //↓5로 설정 했으므로 최대 5개의 이동할 페이지 번호만 보여줌
        model.addAttribute("maxPage", 5);

        return "item/itemMng";
    }

}
