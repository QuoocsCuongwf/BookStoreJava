package com.example.demo.spring_controller;

import com.example.demo.model.NhanVien;
import com.example.demo.services.NhanVienServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/nhanVien")
public class NhanVienControllerSpring {
    NhanVienServices nhanVienServices=new NhanVienServices();
    @GetMapping("/getAllNhanVien")
    public List<NhanVien> getAllNhanVien() {
        System.out.println(nhanVienServices.getNhanVienList());
        return nhanVienServices.getNhanVienList();
    }



}
