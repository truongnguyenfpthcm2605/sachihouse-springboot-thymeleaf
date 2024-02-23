package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.dtos.request.HouseDTO;
import com.shachi.shachihouse.entities.Category;
import com.shachi.shachihouse.entities.House;
import com.shachi.shachihouse.exception.RuntimeExceptionCustom;
import com.shachi.shachihouse.service.impl.CategoryServiceImpl;
import com.shachi.shachihouse.service.impl.FileServiceImpl;
import com.shachi.shachihouse.service.impl.HouseServiceImpl;
import com.shachi.shachihouse.utils.Common;
import com.shachi.shachihouse.utils.Excel;
import com.shachi.shachihouse.utils.SortAndPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final CategoryServiceImpl categoryService;
    private final FileServiceImpl fileService;
    private final HouseServiceImpl houseService;
    private final Excel excel;
    private final Integer pageSize = 3;

    @ModelAttribute("categories")
    public List<Category> getCategories(){
        return categoryService.findAll();
    }

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page", required = false) String pageParam,
                       @RequestParam(value = "categoryTitle", defaultValue = "") String title,
                       @RequestParam(value= "keyword", defaultValue = "") String keyword){
        excel.Export(houseService.findAll());
        int page = Common.handlePage(pageParam);
        Page<House> list =  houseService.findByKeyword(keyword, title,SortAndPage.getPage(page, pageSize, SortAndPage.getSortDown("title")));
        model.addAttribute("list", list);
        model.addAttribute("page", page+1);
        model.addAttribute("keyW", keyword);
        model.addAttribute("cTitle", title);
        model.addAttribute("all","");
        return "Admin/listhouse";
    }



    @GetMapping("/addhouse")
    public String addHouse(Model model){
        model.addAttribute("house", new HouseDTO());
        return "Admin/addHouse";
    }

    @GetMapping("/editor/{id}")
    public String editHouse(@PathVariable("id") String id, Model model) throws RuntimeExceptionCustom {
        House house = houseService.findById(id).get();
        HouseDTO houseDTO = new HouseDTO().builder()
                .id(house.getId())
                .title(house.getTitle())
                .bedroom(house.getBedroom())
                .toilet(house.getToilet())
                .address(house.getAddress())
                .price(house.getPrice())
                .customer(house.getCustomer())
                .description(house.getDescription()).build();
        model.addAttribute("images", house.getImages().split(","));
        model.addAttribute("house",houseDTO);
        return "Admin/addHouse";

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
                    .customer(houseDTO.getCustomer())
                    .price(houseDTO.getPrice())
                    .images(String.join(",",fileService.uploadFiles(multipartFiles,"images")))
                    .isactive(true)
                    .createdate(Common.dateNow)
                    .updatedate(Common.dateNow)
                    .category(categoryService.findById(categoryID).get())
                    .view(1L)
                    .build()));
        }
        return "redirect:/admin/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id){
        houseService.deleteById(id);
        return "redirect:/admin/list";
    }









}
