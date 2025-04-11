package com.example.demo.BUS.spring_controller;


import com.example.demo.BUS.services.SanPhamServices;
import com.example.demo.model.SanPham;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sanPham")
public class SanPhamControllerSpring {
    SanPhamServices sanPhamServices=new SanPhamServices();
    @GetMapping("/getAllSanPham")
    public List<SanPham> getAllSanPham() {
        System.out.println("add sucess");
        return sanPhamServices.getListSanPham();
    }
}
