package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.service.FileManagerService;
import com.shachi.shachihouse.service.impl.FileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final FileServiceImpl fileService;

    private final FileManagerService fileManagerService;
    @GetMapping("/index")
    @PreAuthorize("hasRole('ADMIN')")
    public String index(){
        return "/Web/index";
    }

    @PostMapping ("/uploadFile")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String upload(@RequestParam("file")MultipartFile[] multipartFile,@RequestParam("folder") String folderName) throws IOException {
        return fileManagerService.getListFileName(multipartFile,folderName).get(0).toString();
    }
}
