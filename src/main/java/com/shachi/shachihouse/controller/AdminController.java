package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.dtos.request.HouseDTO;
import com.shachi.shachihouse.entities.Account;
import com.shachi.shachihouse.entities.Category;
import com.shachi.shachihouse.entities.House;
import com.shachi.shachihouse.exception.RuntimeExceptionCustom;
<<<<<<< Updated upstream
import com.shachi.shachihouse.service.CategoryService;
=======
import com.shachi.shachihouse.service.AccountService;
import com.shachi.shachihouse.service.CategoryService;
import com.shachi.shachihouse.service.FileService;
import com.shachi.shachihouse.service.HouseService;
>>>>>>> Stashed changes
import com.shachi.shachihouse.service.impl.AccountServiceImpl;
import com.shachi.shachihouse.service.impl.CategoryServiceImpl;
import com.shachi.shachihouse.service.impl.FileServiceImpl;
import com.shachi.shachihouse.service.impl.HouseServiceImpl;
import com.shachi.shachihouse.utils.Common;
import com.shachi.shachihouse.utils.Excel;
import com.shachi.shachihouse.utils.SortAndPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;
<<<<<<< Updated upstream
    private final FileServiceImpl fileService;
    private final HouseServiceImpl houseService;
=======
    private final FileService fileService;
    private final HouseService houseService;
>>>>>>> Stashed changes
    private final Excel excel;
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.findAll();
    }



    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page", required = false) String pageParam,
                       @RequestParam(value = "categoryTitle", defaultValue = "") String title,
                       @RequestParam(value = "keyword", defaultValue = "") String keyword) {

        excel.Export(houseService.findAll());
        int page = Common.handlePage(pageParam);
        Page<House> list = houseService.findByKeyword(keyword, title, SortAndPage.getPage(page, 3, SortAndPage.getSortDown("title")));
        model.addAttribute("list", list);
        model.addAttribute("page", page + 1);
        model.addAttribute("keyW", keyword);
        model.addAttribute("cTitle", title);
        model.addAttribute("all", "");
        return "Admin/listhouse";
    }


    @GetMapping("/addhouse")
    public String addHouse(Model model) {

        model.addAttribute("house", new HouseDTO());
        return "Admin/addHouse";
    }

    @GetMapping("/editor/{id}")
    public String editHouse(@PathVariable("id") String id, Model model) throws RuntimeExceptionCustom {

        House house = houseService.findById(id).get();
        HouseDTO houseDTO = HouseDTO.builder()
                .id(house.getId())
                .title(house.getTitle())
                .bedroom(house.getBedroom())
                .toilet(house.getToilet())
                .address(house.getAddress())
                .price(house.getPrice())
                .video(house.getVideo())
                .customer(house.getCustomer())
                .description(house.getDescription()).build();
        model.addAttribute("images", house.getImages().split(","));
        model.addAttribute("house", houseDTO);
        return "Admin/addHouse";

    }


    @PostMapping("/save/house")
    public String save(@Validated @ModelAttribute("house") HouseDTO houseDTO,
                       Errors errors,
                       Model model,
                       @RequestParam("category") Long categoryID,
                       @RequestParam("images") MultipartFile[] multipartFiles) {

        if (errors.hasErrors()) {
            model.addAttribute("messageAll", "Thông tin chưa đủ!");
        } else {
            if(Objects.isNull(multipartFiles)){
                if(houseService.findById(houseDTO.getId()).orElse(null) == null){
                    model.addAttribute("messageAll", "Vui lòng chọn hình ảnh");
                    return "Admin/addHouse";
                }
            }
            model.addAttribute("messageAll", houseService.save(House.builder()
                    .id(houseDTO.getId())
                    .title(houseDTO.getTitle())
                    .bedroom(houseDTO.getBedroom())
                    .toilet(houseDTO.getToilet())
                    .address(houseDTO.getAddress())
                    .description(houseDTO.getDescription())
                    .customer(houseDTO.getCustomer())
                    .video(houseDTO.getVideo())
                    .price(houseDTO.getPrice())
                    .images(String.join(",", fileService.uploadFiles(multipartFiles, "images")))
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
    public String delete(@PathVariable("id") String id) {
        houseService.deleteById(id);
        return "redirect:/admin/list";
    }


    @GetMapping("/changepass")
    public String changePassword() {
        return "Admin/changepass";
    }

    @PostMapping("/changepass")
    public String changePassword(@RequestParam("password") String password,
                                 @RequestParam("email") String email,
                                 @RequestParam("newpass") String newpass,
                                 Model model) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            if (Objects.nonNull(authentication.getPrincipal())) {
                Optional<Account> account = accountService.findByEmail(email);
                account.get().setPassword(passwordEncoder.encode(newpass));
                accountService.update(account.get());

                SecurityContextHolder.clearContext();
                return "redirect:/index";
            }
        } catch (Exception e) {
            model.addAttribute("messagechange", "Sai thông tin");
            return "Admin/changepass";
        }
        return "redirect:/index";
    }


}
