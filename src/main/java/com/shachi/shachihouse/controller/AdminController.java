package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.dtos.request.HouseDTO;
import com.shachi.shachihouse.entities.Category;
import com.shachi.shachihouse.entities.House;
import com.shachi.shachihouse.service.impl.CategoryServiceImpl;
import com.shachi.shachihouse.service.impl.FileServiceImpl;
import com.shachi.shachihouse.service.impl.HouseServiceImpl;
import com.shachi.shachihouse.utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final CategoryServiceImpl categoryService;
    private final FileServiceImpl fileService;
    private final HouseServiceImpl houseService;

    @ModelAttribute("categories")
    public List<Category> getCategories(){
        return categoryService.findAll();
    }

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("list", houseService.findAll());
        return "/Admin/listhouse";
    }

    @GetMapping("/addhouse")
    public String addHouse(Model model){
        model.addAttribute("house", new HouseDTO());
        return "/Admin/addHouse";
    }

    @PostMapping("/save/house")
    public String save(@Validated  @ModelAttribute("house")HouseDTO houseDTO,
                       Errors errors,
                       Model model,
                       @RequestParam("category") Long categoryID,
                       @RequestParam("images")MultipartFile[] multipartFiles){
        if(errors.hasErrors()){
            model.addAttribute("messageAll","Thông tin chưa đủ!");
        }else{
            model.addAttribute("messageAll",houseService.save( House.builder()
                    .id(houseDTO.getId())
                    .title(houseDTO.getTitle())
                    .bedroom(houseDTO.getBedroom())
                    .toilet(houseDTO.getToilet())
                    .address(houseDTO.getAddress())
                    .description(houseDTO.getDescription())
                    .price(houseDTO.getPrice())
                    .images(String.join(",",fileService.uploadFiles(multipartFiles,"images")))
                    .isactive(true)
                    .createdate(Common.dateNow)
                    .updatedate(Common.dateNow)
                    .category(categoryService.findById(categoryID).get())
                    .view(1L)
                    .build()));
        }
        return "/Admin/listhouse";
    }





}
