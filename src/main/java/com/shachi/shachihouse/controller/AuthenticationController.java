package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.entities.Account;
import com.shachi.shachihouse.service.impl.AccountServiceImpl;
import com.shachi.shachihouse.utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AccountServiceImpl accountService;

    @GetMapping("/auth/oauth2")
    public ResponseEntity<Object> authLogin() {
        Account account = accountService.findByUsernameAndProviderID(Common.email_OAuth2, Common.providerId).orElseThrow(() -> new UsernameNotFoundException("Account not found"));
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

}
