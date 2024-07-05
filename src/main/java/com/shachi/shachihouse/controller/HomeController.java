package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.dtos.request.InformationDTO;
import com.shachi.shachihouse.entities.Account;
import com.shachi.shachihouse.entities.Category;
import com.shachi.shachihouse.entities.House;
import com.shachi.shachihouse.entities.Information;
import com.shachi.shachihouse.mail.MailerService;
import com.shachi.shachihouse.service.AccountService;
import com.shachi.shachihouse.service.CategoryService;
import com.shachi.shachihouse.service.HouseService;
import com.shachi.shachihouse.service.InformationService;
import com.shachi.shachihouse.utils.Common;
import com.shachi.shachihouse.utils.Session;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HouseService houseService;
    private final CategoryService categoryService;
    private final InformationService informationService;
    private final Session session;
    private final MailerService mailerService;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping({ "/index", "/" })
    public String index(Model model) {
        setUpCategoryAttributes(model);
        List<List<House>> houseLists = new ArrayList<>();
        for (Category category : categoryService.findAll()) {
            houseLists.add(houseService.findByCategoryId(category.getId()));
        }
        model.addAttribute("houseLists", houseLists);
        return "home/index";
    }

    @GetMapping("/houses-by-category/{categoryId}")
    public String getHousesByCategory(Model model, @PathVariable Long categoryId) {
        model.addAttribute("houses", houseService.findByCategoryId(categoryId));
        model.addAttribute("selectedCategory", categoryService.findById(categoryId));
        session.setAttribute("categoryId", categoryId);
        setUpCategoryAttributes(model);
        return "home/rooms";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        setUpCategoryAttributes(model);
        return "home/contact";
    }

    @GetMapping("/about")
    public String about(Model model) {
        setUpCategoryAttributes(model);
        return "home/about";
    }

    @GetMapping("/rooms")
    public String room(Model model) {
        model.addAttribute("houses", houseService.findAll());
        setUpCategoryAttributes(model);
        return "home/rooms";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable String id) {
        model.addAttribute("house", houseService.findById(id).orElse(null));
        setUpCategoryAttributes(model);
        return "home/rooms-single";
    }


    @GetMapping("/bedroom/search")
    public String performSearch(@RequestParam(name = "bedrooms") int bedrooms, Model model) {
        Long categoryId = (Long) session.getAttribute("categoryId");
        model.addAttribute("houses", houseService.searchByBedrooms(bedrooms, categoryId));
        setUpCategoryAttributes(model);
        return "home/rooms";
    }

    @PostMapping("/bedroom/search")
    public String performSearchPost(@RequestParam("bedrooms") int bedrooms, Model model) {
        Long categoryId = (Long) session.getAttribute("categoryId");
        model.addAttribute("searchResults", houseService.searchByBedrooms(bedrooms, categoryId));
        setUpCategoryAttributes(model);
        return "home/rooms";
    }


    @PostMapping("/customer/search")
    public String performSearch(@RequestParam("customer") String customer, Model model) {
        Long categoryId = (Long) session.getAttribute("categoryId");
        model.addAttribute("searchResults", houseService.searchByCustomer("%" + customer + "%", categoryId));
        setUpCategoryAttributes(model);
        return "home/rooms";
    }

    private void setUpCategoryAttributes(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("homestayCategories", filterCategoriesByTitle(categories, "HOMESTAY"));
        model.addAttribute("villaCategories", filterCategoriesByTitle(categories, "VILLA"));
        model.addAttribute("otherCategories", filterCategoriesByTitle(categories, null));
    }

    private List<Category> filterCategoriesByTitle(List<Category> categories, String title) {
        return categories.stream()
                .filter(category -> title == null
                        ? !category.getTitle().contains("HOMESTAY") && !category.getTitle().contains("VILLA")
                        : category.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @ModelAttribute("information")
    public InformationDTO getInformationDTO() {
        return new InformationDTO();
    }

    @PostMapping("/information/save")
    public String save(@ModelAttribute("information") InformationDTO informationDTO) {
        informationService.save(Information.builder()
                .phone(informationDTO.getPhone())
                .email(informationDTO.getEmail())
                .fullname(informationDTO.getFullname())
                .isactive(true).build());

        return "redirect:/index";
    }

    @GetMapping("/remmemberPass")
    public String remmemberPass() {
        return "home/remember";
    }

    @PostMapping("/sendmail")
    public String sendMail(@RequestParam(value = "mail", defaultValue = "") String mail, Model model) {

        try {
            String passRandom = Common.randomCodeMail();
            Optional<Account> account = accountService.findByEmail(mail);

            if (account.isEmpty()) {
                model.addAttribute("messageRemember", "Email tài khoản không tồn tại");
                return "home/remember";
            } else {
                account.get().setPassword(passwordEncoder.encode(passRandom));
                accountService.update(account.get());
                mailerService.send(mail, "Mail xác thực tài khoản từ Sachi House",
                        "  <div style=width:80%; margin:0 auto;text-align: center ;>\n" +
                                "    <h1 style=color:#080202 ;>Sachi House Đà Lạt</h1>\n" +
                                "    <p>Dùng mã này để xác minh địa chỉ email của bạn trên Sachi House </p>\n" +
                                "    <p>Xin chào Bạn,Chúng tôi cần xác minh địa chỉ email của bạn để đảm bảo là có thể liên hệ với bạn sau khi xem xét\n"
                                +
                                "      ID.</p>\n" +
                                "    <p>Chúng tôi cần xác minh địa chỉ email của bạn để đảm bảo là có thể liên hệ với bạn sau khi xem xét ID.</p>\n"
                                +
                                "    <h5>Mật khẩu mới</h5>" +
                                "<h2 style=color: #116D6E;>" + passRandom + "</h2>" +
                                "      <br>" +
                                "    <p style=font-size: 15px;font-weight: 200;>Tin nhắn này được gửi tới bạn theo yêu cầu của Sachi House.\n"
                                +
                                "      Sachi House © 2024 All rights re6served. Privacy Policy|T&C|System Status</p>\n"
                                +
                                "  </div>");
            }

        } catch (MessagingException e) {
            model.addAttribute("messageRemember", "Email tài khoản không tồn tại");
            return "home/remember";
        }
        model.addAttribute("messageRemember", "Mật khẩu mới đã được gửi về email");
        return "home/remember";
    }

}