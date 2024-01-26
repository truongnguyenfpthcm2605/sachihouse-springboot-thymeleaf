package com.shachi.shachihouse.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/index")
    @PreAuthorize("hasRole('ADMIN')")
    public String index(){
        return "/Web/index";
    }
}
