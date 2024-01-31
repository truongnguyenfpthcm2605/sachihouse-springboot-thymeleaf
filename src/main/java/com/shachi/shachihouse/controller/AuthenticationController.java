package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.dtos.request.AccountDTO;
import com.shachi.shachihouse.entities.Account;
import com.shachi.shachihouse.entities.Role;
import com.shachi.shachihouse.service.impl.AccountServiceImpl;
import com.shachi.shachihouse.service.impl.RoleServiceImpl;
import com.shachi.shachihouse.utils.Common;
import com.shachi.shachihouse.utils.Provider;
import com.shachi.shachihouse.utils.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AccountServiceImpl accountService;
    private final RoleServiceImpl roleService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/auth/oauth2")
    public ResponseEntity<Object> authLogin() {
        Account account = accountService.findByUsernameAndProviderID(Common.email_OAuth2, Common.providerId).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/auth/oauth2/fail")
    public String loginFailOAuth2(Model model) {
        model.addAttribute("message", "Đăng nhập thất bại!");
        return "/auth/login";
    }

    @GetMapping("/auth/login/form")
    public String loginForm() {
        return "/auth/login";
    }

    @GetMapping("/auth/login/success")
    public String loginSuccess() {
        return "/home/index";
    }

    @GetMapping("/auth/logout/success")
    public String logoutSuccess() {
        return "/home/index";
    }

    @GetMapping("/auth/login/error")
    public String loginFail(Model model) {
        model.addAttribute("message", "Đăng nhập thất bại!");
        return "/auth/login";
    }

    @GetMapping("/auth/denied")
    public String loginDenied(Model model) {
        model.addAttribute("message", "Không có quyền truy cập!");
        return "/auth/login";
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public ResponseEntity<Object> register(@Validated @RequestBody AccountDTO accountDTO, Errors errors) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByName(Roles.USER).get());
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
        return new ResponseEntity<>(account , HttpStatus.OK );
    }


}