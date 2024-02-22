package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.dtos.request.InformationDTO;
import com.shachi.shachihouse.entities.Information;
import com.shachi.shachihouse.service.impl.InformationServiceImpl;
import com.shachi.shachihouse.utils.Common;
import com.shachi.shachihouse.utils.Excel;
import com.shachi.shachihouse.utils.SortAndPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequiredArgsConstructor
@RequestMapping("admin/information")
public class InformationController {

    private final InformationServiceImpl informationService;
    private final Excel excel;
    private final Integer PAGE_SIZE = 10;



    @GetMapping("/")
    public String getInformation(
        @RequestParam(value = "page", required = false) String page,
        @RequestParam(value = "title", defaultValue = "") String title,
        Model model
    ){
        excel.ExportInformation(informationService.findAll());
        int p = Common.handlePage(page);
        Page<Information> list = informationService.findAll("%"+title+"%",SortAndPage.getPage(p,PAGE_SIZE,SortAndPage.getSortDown("email")));
        model.addAttribute("list", list);
        model.addAttribute("page", p+1);
        model.addAttribute("title", title);
        model.addAttribute("information", new InformationDTO());
        return "Admin/information";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("information") InformationDTO informationDTO
                       ) {

            informationService.save(Information.builder()
                    .phone(informationDTO.getPhone())
                    .email(informationDTO.getEmail())
                    .fullname(informationDTO.getFullname())
                    .isactive(true).build());

        return "redirect:/admin/information";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        informationService.deleteById(id);
        return "redirect:/admin/information";
    }




}
