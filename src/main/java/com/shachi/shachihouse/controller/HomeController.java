package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.service.impl.FileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final FileServiceImpl fileService;


    @GetMapping("/index")
   // @PreAuthorize("hasRole('ADMIN')")
    public String index(){
        return "/Web/index";
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
