package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.entities.Category;
import com.shachi.shachihouse.entities.House;
import com.shachi.shachihouse.service.impl.CategoryServiceImpl;
import com.shachi.shachihouse.service.impl.FileServiceImpl;
import com.shachi.shachihouse.service.impl.HouseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final FileServiceImpl fileService;
    private final HouseServiceImpl houseService;
    private final CategoryServiceImpl categoryService;


    @GetMapping("/index")
    public String index(){
        List<House> houses = houseService.findAll();
        List<Category> categories = categoryService.findAll();
        return "home/index";
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
