package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.dtos.request.AccountDTO;
import com.shachi.shachihouse.dtos.request.InformationDTO;
import com.shachi.shachihouse.entities.Account;
import com.shachi.shachihouse.entities.Category;
import com.shachi.shachihouse.entities.House;
import com.shachi.shachihouse.entities.Role;
import com.shachi.shachihouse.service.AccountService;
import com.shachi.shachihouse.service.CategoryService;
import com.shachi.shachihouse.service.HouseService;
import com.shachi.shachihouse.service.RoleService;
import com.shachi.shachihouse.service.impl.AccountServiceImpl;
import com.shachi.shachihouse.service.impl.CategoryServiceImpl;
import com.shachi.shachihouse.service.impl.HouseServiceImpl;
import com.shachi.shachihouse.service.impl.RoleServiceImpl;
import com.shachi.shachihouse.utils.Common;
import com.shachi.shachihouse.utils.Provider;
import com.shachi.shachihouse.utils.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final HouseService houseService;
    private final CategoryService categoryService;
    private final AccountService accountService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;



    @GetMapping("/auth/login/form")
    public String loginForm( Model model) {
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
                otherCategories.add(category);
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
        return "home/login";
    }

    @GetMapping("/auth/login/success")
    public String loginSuccess() {
        return "redirect:/index";
    }

    @GetMapping("/auth/logout/success")
    public String logoutSuccess() {
        return "redirect:/index";
    }

    @GetMapping("/auth/login/error")
    public String loginFail(Model model) {
        model.addAttribute("message", "Đăng nhập thất bại!");
        return "home/login";
    }

    @GetMapping("/auth/denied")
    public String loginDenied(Model model) {
        model.addAttribute("message", "Không có quyền truy cập!");
        return "home/login";
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public ResponseEntity<Object> register(@Validated @RequestBody AccountDTO accountDTO, Errors errors) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByName(Roles.ADMIN).get());
        if (errors.hasErrors()) {
            return new ResponseEntity<>("Lỗi Validation!", HttpStatus.BAD_REQUEST);
        } else if (accountService.findByEmail(accountDTO.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email đã tồn tại!", HttpStatus.BAD_REQUEST);
        } else if (accountService.findByUsername(accountDTO.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username đã tồn tại!", HttpStatus.BAD_REQUEST);
        }
        Account account = accountService.save(Account.builder()
                .email(accountDTO.getEmail())
                .username(accountDTO.getUsername())
                .providerID(Provider.local.name())
                .fullname(accountDTO.getFullname())
                .password(passwordEncoder.encode(accountDTO.getPassword()))
                .createdate(Common.dateNow)
                .updatedate(Common.dateNow)
                .images(accountDTO.getImages())
                .isactive(true)
                .roles(roles)
                .build()
        );
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @ModelAttribute("information")
    public InformationDTO getInformationDTO(){
        return new InformationDTO();
    }




}
