package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.dtos.request.InformationDTO;
import com.shachi.shachihouse.entities.Category;
import com.shachi.shachihouse.entities.House;
import com.shachi.shachihouse.entities.Information;
import com.shachi.shachihouse.service.impl.CategoryServiceImpl;
import com.shachi.shachihouse.service.impl.HouseServiceImpl;
import com.shachi.shachihouse.service.impl.InformationServiceImpl;
import com.shachi.shachihouse.utils.Common;
import com.shachi.shachihouse.utils.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Controller
@RequiredArgsConstructor
public class HomeController {


    private final HouseServiceImpl houseService;
    private final CategoryServiceImpl categoryService;
    private final InformationServiceImpl informationService;
    private final Session session;


    @GetMapping({"/index","/"})
    public String index(Model model) {
        List<Category> categories = categoryService.findAll();
        List<Category> homestayCategories = new ArrayList<>();
        List<Category> villaCategories = new ArrayList<>();
        List<Category> otherCategories = new ArrayList<>();

        for (Category category : categories) {
            if (category.getTitle().contains("HOMESTAY")) {
                homestayCategories.add(category);
            } else if (category.getTitle().contains("VILLA")) {
                villaCategories.add(category);
            } else {
                otherCategories.add(category); // Thêm vào danh sách danh mục khác
            }
        }

        List<List<House>> houseLists = new ArrayList<>();

        for (Category category : categories) {
            List<House> categoryHouses = houseService.findByCategoryId(category.getId());
            houseLists.add(categoryHouses);
        }

        model.addAttribute("categories", categories);
        model.addAttribute("homestayCategories", homestayCategories);
        model.addAttribute("villaCategories", villaCategories);
        model.addAttribute("otherCategories", otherCategories);
        model.addAttribute("houseLists", houseLists);

        return "home/index";
    }
    @GetMapping("/houses-by-category/{categoryId}")
    public String getHousesByCategory(Model model, @PathVariable Long categoryId) {
        List<House> houses = houseService.findByCategoryId(categoryId);
        List<Category> categories = categoryService.findAll();
        Optional<Category> selectedCategory = categoryService.findById(categoryId);

        model.addAttribute("selectedCategory", selectedCategory);
        session.setAttribute("categoryId",categoryId);

        model.addAttribute("houses", houses);
        model.addAttribute("categories", categories);


        return "home/rooms";
    }

    @GetMapping("/contact")
    public  String contact(Model model){
        List<Category> categories = categoryService.findAll();
        List<Category> homestayCategories = new ArrayList<>();
        List<Category> villaCategories = new ArrayList<>();
        List<Category> otherCategories = new ArrayList<>();
        for (Category category : categories) {
            if (category.getTitle().contains("HOMESTAY")) {
                homestayCategories.add(category);
            } else if (category.getTitle().contains("VILLA")) {
                villaCategories.add(category);
            } else {
                otherCategories.add(category); // Thêm vào danh sách danh mục khác
            }
        }
        model.addAttribute("categories", categories);
        model.addAttribute("homestayCategories", homestayCategories);
        model.addAttribute("villaCategories", villaCategories);
        model.addAttribute("otherCategories", otherCategories);
        return "home/contact";
    }
    @GetMapping("/about")
    public  String about(Model model){
        List<Category> categories = categoryService.findAll();
        List<Category> homestayCategories = new ArrayList<>();
        List<Category> villaCategories = new ArrayList<>();
        List<Category> otherCategories = new ArrayList<>();
        for (Category category : categories) {
            if (category.getTitle().contains("HOMESTAY")) {
                homestayCategories.add(category);
            } else if (category.getTitle().contains("VILLA")) {
                villaCategories.add(category);
            } else {
                otherCategories.add(category); // Thêm vào danh sách danh mục khác
            }
        }
        model.addAttribute("categories", categories);
        model.addAttribute("homestayCategories", homestayCategories);
        model.addAttribute("villaCategories", villaCategories);
        model.addAttribute("otherCategories", otherCategories);
        return "home/about";
    }
    @GetMapping("/rooms")
    public String room(Model model) {
        List<House> houses = houseService.findAll();
        List<Category> categories = categoryService.findAll();
        List<Category> homestayCategories = new ArrayList<>();
        List<Category> villaCategories = new ArrayList<>();
        List<Category> otherCategories = new ArrayList<>();

        for (Category category : categories) {
            if (category.getTitle().contains("HOMESTAY")) {
                homestayCategories.add(category);
            } else if (category.getTitle().contains("VILLA")) {
                villaCategories.add(category);
            } else {
                otherCategories.add(category); // Thêm vào danh sách danh mục khác
            }
        }
        model.addAttribute("houses", houses);
        model.addAttribute("categories", categories);
        model.addAttribute("homestayCategories", homestayCategories);
        model.addAttribute("villaCategories", villaCategories);
        model.addAttribute("otherCategories", otherCategories);



        return "home/rooms";

    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable String   id){
        Optional<House> house = houseService.findById(id);
        List<Category> categories = categoryService.findAll();

        List<Category> homestayCategories = new ArrayList<>();
        List<Category> villaCategories = new ArrayList<>();
        List<Category> otherCategories = new ArrayList<>();

        for (Category category : categories) {
            if (category.getTitle().contains("HOMESTAY")) {
                homestayCategories.add(category);
            } else if (category.getTitle().contains("VILLA")) {
                villaCategories.add(category);
            } else {
                otherCategories.add(category); // Thêm vào danh sách danh mục khác
            }
        }
        model.addAttribute("house", house.orElse(null));
        model.addAttribute("categories", categories);
        model.addAttribute("homestayCategories", homestayCategories);
        model.addAttribute("villaCategories", villaCategories);
        model.addAttribute("otherCategories", otherCategories);
        return "home/rooms-single";
    }

    @GetMapping("/bedroom/search")
    public String performSearch(Model model, @RequestParam(name = "bedrooms") int bedrooms) {
        List<House> searchResults = houseService.searchByBedrooms(bedrooms,session.getAttribute("categoryId"));
        List<Category> categories = categoryService.findAll();

        List<Category> homestayCategories = new ArrayList<>();
        List<Category> villaCategories = new ArrayList<>();
        List<Category> otherCategories = new ArrayList<>();

        for (Category category : categories) {
            if (category.getTitle().contains("HOMESTAY")) {
                homestayCategories.add(category);
            } else if (category.getTitle().contains("VILLA")) {
                villaCategories.add(category);
            } else {
                otherCategories.add(category); // Thêm vào danh sách danh mục khác
            }
        }
        model.addAttribute("houses", searchResults);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("categories", categories);
        model.addAttribute("homestayCategories", homestayCategories);
        model.addAttribute("villaCategories", villaCategories);
        model.addAttribute("otherCategories", otherCategories);
        return "home/rooms";
    }

    @PostMapping("/bedroom/search")
    public String performSearch(@RequestParam("bedrooms") int bedrooms, Model model) {
        Long categoryId = session.getAttribute("categoryId");
        List<House> searchResults = houseService.searchByBedrooms(bedrooms, categoryId);
        List<Category> categories = categoryService.findAll();

        List<Category> homestayCategories = new ArrayList<>();
        List<Category> villaCategories = new ArrayList<>();
        List<Category> otherCategories = new ArrayList<>();

        for (Category category : categories) {
            if (category.getTitle().contains("HOMESTAY")) {
                homestayCategories.add(category);
            } else if (category.getTitle().contains("VILLA")) {
                villaCategories.add(category);
            } else {
                otherCategories.add(category); // Thêm vào danh sách danh mục khác
            }
        }
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("categories", categories);
        model.addAttribute("homestayCategories", homestayCategories);
        model.addAttribute("villaCategories", villaCategories);
        model.addAttribute("otherCategories", otherCategories);
        return "home/rooms";
    }

    @PostMapping("/customer/search")
    public String performSearch(@RequestParam("customer") String customer, Model model) {
        Long categoryId = session.getAttribute("categoryId");
        List<House> searchResults = houseService.searchByCustomer("%"+customer+"%", categoryId);
        List<Category> categories = categoryService.findAll();

        List<Category> homestayCategories = new ArrayList<>();
        List<Category> villaCategories = new ArrayList<>();
        List<Category> otherCategories = new ArrayList<>();

        for (Category category : categories) {
            if (category.getTitle().contains("HOMESTAY")) {
                homestayCategories.add(category);
            } else if (category.getTitle().contains("VILLA")) {
                villaCategories.add(category);
            } else {
                otherCategories.add(category); // Thêm vào danh sách danh mục khác
            }
        }
        model.addAttribute("searchResults", searchResults);
        model.addAttribute("categories", categories);
        model.addAttribute("homestayCategories", homestayCategories);
        model.addAttribute("villaCategories", villaCategories);
        model.addAttribute("otherCategories", otherCategories);
        return "home/rooms";
    }




    @ModelAttribute("information")
    public InformationDTO getInformationDTO(){
        return new InformationDTO();
    }

    @ModelAttribute("account")
    public Object getAccount(){
        return  session.getAttribute(Common.ACCOUNT_SESSION);
    }

    @PostMapping("/information/save")
    public String save(@ModelAttribute("information") InformationDTO informationDTO
    ) {
        informationService.save(Information.builder()
                .phone(informationDTO.getPhone())
                .email(informationDTO.getEmail())
                .fullname(informationDTO.getFullname())
                .isactive(true).build());

        return "redirect:/index";
    }








}