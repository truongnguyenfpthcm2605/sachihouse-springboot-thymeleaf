package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.dtos.request.HouseDTO;
import com.shachi.shachihouse.entities.Category;
import com.shachi.shachihouse.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final CategoryServiceImpl categoryService;

    @ModelAttribute("categories")
    public List<Category> getCategories(){
        return categoryService.findAll();
    }

    @GetMapping("/addhouse")
    public String addHouse(Model model){
        model.addAttribute("house", new HouseDTO());
        return "Admin/addHouse";
    }

    @PostMapping("/save/house")
    public String save(@Validated  @ModelAttribute("house")HouseDTO houseDTO,
                       Errors errors,
                       Model model,
                       @RequestParam("category") Integer categoryID,
                       @RequestParam("images")MultipartFile[] multipartFiles){
        if(errors.hasErrors()){
            model.addAttribute("messageAll","Thông tin chưa đủ!");
        } else if (Objects.isNull(multipartFiles)) {
            model.addAttribute("messageImg","Chưa chọn ảnh !");
        }else{
            model.addAttribute("messageAll", houseDTO);
        }
        return "/Admin/addHouse";
    }


}
