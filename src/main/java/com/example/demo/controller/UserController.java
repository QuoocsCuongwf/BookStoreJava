//package com.example.demo.controller;
//
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
///*
//  @Controller ->HTML + @Responsibly = string
//  @RestController ->string
//  @RequestMapping -> register url
// */
//@RestController
//@RequestMapping("/user")
//public class UserController {
//    @GetMapping("/addUser")
//    public String addUser() {
//        return "addUser";
//    }
//    @GetMapping("/returnUser")
//    public String returnUser() {
//        return "returnUser";
//    }
//
//    @GetMapping("/deleteUser/{id}/{name}")
//    public String deleteUser(@PathVariable("id") int id,@PathVariable String name) {
//        return "deletedUser"+id+name;
//    }
//    @PostMapping("/update")
//    public String updateUser(@RequestBody List<User> user) {
//        for (User user1 : user) {
//            System.out.println(user1.getName()+" "+user1.getEmail());
//        }
//        return "updateUser";
//    }
//}
