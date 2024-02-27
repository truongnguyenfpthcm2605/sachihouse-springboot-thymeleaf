package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.entities.Category;
import com.shachi.shachihouse.entities.House;
import com.shachi.shachihouse.service.CategoryService;
import com.shachi.shachihouse.service.impl.CategoryServiceImpl;
import com.shachi.shachihouse.service.impl.FileServiceImpl;
import com.shachi.shachihouse.service.impl.HouseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final FileServiceImpl fileService;
    private final HouseServiceImpl houseService;
    private final CategoryServiceImpl categoryService;



    @GetMapping("/index")
    public String index(Model model) {
        List<House> houses = houseService.findAll();
        List<Category> categories = categoryService.findAll();
        List<List<Category>> categoryLists = new ArrayList<>();
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

        categoryLists.add(homestayCategories); // Add each category list to the main list
        categoryLists.add(villaCategories);
        categoryLists.add(otherCategories);

        model.addAttribute("houses", houses);
        model.addAttribute("categories", categories);
        model.addAttribute("homestayCategories", homestayCategories);
        model.addAttribute("villaCategories", villaCategories);
        model.addAttribute("otherCategories", otherCategories);
        model.addAttribute("categoryLists", categoryLists);

        Map<Category, List<House>> categoryHouseMap = new HashMap<>();

        for (Category category : categories) {
            List<House> categoryHouses = new ArrayList<>();
            int count = 0;

            for (House house : houses) {
                if (house.getCategory().equals(category) && count < 4) {
                    categoryHouses.add(house);
                    count++;
                }
            }

            categoryHouseMap.put(category, categoryHouses);
        }

        model.addAttribute("categoryHouseMap", categoryHouseMap);
        return "/home/index";
    }
    @GetMapping("/houses-by-category/{categoryId}")
    public String getHousesByCategory(Model model, @PathVariable Long categoryId) {
        List<House> houses = houseService.findByCategoryId(categoryId);
        List<Category> categories = categoryService.findAll();


        model.addAttribute("houses", houses);
        model.addAttribute("categories", categories);


        return "/home/rooms";
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
        return "/home/rooms";
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
        return "/home/rooms-single";
    }

    @GetMapping("/bedroom/search")
    public String performSearch(Model model, @RequestParam(name = "bedrooms") int bedrooms) {
        List<House> searchResults = houseService.searchByBedrooms(bedrooms);

        model.addAttribute("houses", searchResults);
        model.addAttribute("categories", categoryService.findAll());

        return "/home/rooms";
    }

    @PostMapping("/bedroom/search")
    public String performSearch(@RequestParam("bedrooms") int bedrooms, Model model) {
        List<House> searchResults = houseService.searchByBedrooms(bedrooms);

        model.addAttribute("searchResults", searchResults);
        return "home/rooms";
    }


    @PostMapping ("/uploadFile")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String upload(@RequestParam("file")MultipartFile multipartFile,@RequestParam("folder") String folderName) throws IOException {
        return fileService.uploadFileString(multipartFile,folderName);

    }
    @PostMapping ("/downloadFile")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String upload(@RequestParam("fileName") String fileName,@RequestParam("folder") String folderName)  {
        return fileService.read(folderName,fileName);
    }





}