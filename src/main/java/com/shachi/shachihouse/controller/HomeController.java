package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.dtos.request.InformationDTO;
import com.shachi.shachihouse.entities.Account;
import com.shachi.shachihouse.entities.Category;
import com.shachi.shachihouse.entities.House;
import com.shachi.shachihouse.entities.Information;
import com.shachi.shachihouse.mail.MailerServiceImpl;
import com.shachi.shachihouse.service.impl.AccountServiceImpl;
import com.shachi.shachihouse.service.impl.CategoryServiceImpl;
import com.shachi.shachihouse.service.impl.HouseServiceImpl;
import com.shachi.shachihouse.service.impl.InformationServiceImpl;
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

@Controller
@RequiredArgsConstructor
public class HomeController {


    private final HouseServiceImpl houseService;
    private final CategoryServiceImpl categoryService;
    private final InformationServiceImpl informationService;
    private final Session session;
    private final MailerServiceImpl mailerService;
    private final AccountServiceImpl accountService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping({"/index", "/"})
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
        session.setAttribute("categoryId", categoryId);

        model.addAttribute("houses", houses);
        model.addAttribute("categories", categories);


        return "home/rooms";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
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
    public String about(Model model) {
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
    public String detail(Model model, @PathVariable String id) {
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
        List<House> searchResults = houseService.searchByBedrooms(bedrooms, session.getAttribute("categoryId"));
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
        List<House> searchResults = houseService.searchByCustomer("%" + customer + "%", categoryId);
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
    public InformationDTO getInformationDTO() {
        return new InformationDTO();
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

    @GetMapping("/remmemberPass")
    public String remmemberPass() {
        return "home/remember";
    }

    @PostMapping("/sendmail")
    public String sendMail(@RequestParam(value = "mail", defaultValue = "") String mail, Model model) {

        try {
            String passRandom= Common.randomCodeMail();
            Optional<Account> account = accountService.findByEmail(mail);

            if(account.isEmpty()) {
                model.addAttribute("messageRemember", "Email tài khoản không tồn tại");
                return "home/remember";
            }else {
                account.get().setPassword(passwordEncoder.encode(passRandom));
                accountService.update(account.get());
                mailerService.send(mail, "Mail xác thực tài khoản từ Sachi House",
                        "  <div style=width:80%; margin:0 auto;text-align: center ;>\n" +
                                "    <h1 style=color:#080202 ;>Sachi House Đà Lạt</h1>\n" +
                                "    <p>Dùng mã này để xác minh địa chỉ email của bạn trên Sachi House </p>\n" +
                                "    <p>Xin chào Bạn,Chúng tôi cần xác minh địa chỉ email của bạn để đảm bảo là có thể liên hệ với bạn sau khi xem xét\n" +
                                "      ID.</p>\n" +
                                "    <p>Chúng tôi cần xác minh địa chỉ email của bạn để đảm bảo là có thể liên hệ với bạn sau khi xem xét ID.</p>\n" +
                                "    <h5>Mật khẩu mới</h5>" +
                                "<h2 style=color: #116D6E;>" + passRandom + "</h2>" +
                                "      <br>" +
                                "    <p style=font-size: 15px;font-weight: 200;>Tin nhắn này được gửi tới bạn theo yêu cầu của Sachi House.\n" +
                                "      Sachi House © 2024 All rights re6served. Privacy Policy|T&C|System Status</p>\n" +
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