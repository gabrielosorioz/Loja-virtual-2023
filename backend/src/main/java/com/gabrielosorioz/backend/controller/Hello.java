package com.gabrielosorioz.backend.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class Hello {

    @GetMapping("/")
    public String hello(){
        return "HEllo World Spring" + new Date();
    }
    
}
