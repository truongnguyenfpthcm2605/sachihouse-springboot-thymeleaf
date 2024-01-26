package com.shachi.shachihouse.controller;

import com.shachi.shachihouse.entities.Account;
import com.shachi.shachihouse.service.impl.AccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class HealthCheckController {
    private final AccountServiceImpl accountService;
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck(){
        try {
            List<Account> list = accountService.findAll();
            String computer = InetAddress.getLocalHost().getHostName();
            return new ResponseEntity<>("Stronger Server : " + computer, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
