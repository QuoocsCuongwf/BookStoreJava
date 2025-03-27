package com.example.demo.spring_controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Account")
public class AccountControllerSpring {
     @PostMapping("/Login")
     public boolean login(){
          return false;
     }
}
