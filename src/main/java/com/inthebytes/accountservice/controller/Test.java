package com.inthebytes.accountservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @Value("${spring.datasource.username}")
    private String dbUrl;

    @GetMapping("/test")
    String getTest() {
        return dbUrl;
    }

}
