package com.shachi.shachihouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@RequiredArgsConstructor
@RestController
public class HealthCheckController {
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck(){
        try {
            String computer = InetAddress.getLocalHost().getHostName();
            return new ResponseEntity<>("Stronger Server : " + computer, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
