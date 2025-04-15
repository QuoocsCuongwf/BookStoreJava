package com.example.demo.BUS.spring_controller;


import com.example.demo.BUS.services.SanPhamServices;
import com.example.demo.model.SanPham;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/search")
    public List<SanPham> search(@RequestParam String find) {
        return sanPhamServices.searchSanPham(find);
    }
    @PostMapping("/update")
    public void updateSanPham(@RequestBody SanPham sanPham) {
        sanPhamServices.updateSanPham(sanPham);
    }
    @PostMapping("/delete")
    public void deleteSanPham(@RequestParam String maSanPham) {
        sanPhamServices.deleteSanPham(maSanPham);
    }
    @PostMapping("/insert")
    public void insertSanPham(@RequestBody SanPham sanPham) {
        sanPhamServices.insertSanPham(sanPham);
    }
}
