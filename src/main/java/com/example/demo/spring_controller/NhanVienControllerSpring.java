package com.example.demo.spring_controller;

import com.example.demo.model.NhanVien;
import com.example.demo.services.NhanVienServices;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/timKiem")
    public List<NhanVien> timKiem(@RequestParam String find) {
        return nhanVienServices.searchNhanVien(find);
    }

    @PostMapping("/Delete")
    public String DeleteNhanVien(@RequestParam String maNhanVien) {
        nhanVienServices.deleteNhanVien(maNhanVien);
        return "success";
    }
    @PostMapping("/Add")
    public String AddNhanVien(@RequestBody NhanVien nhanVien) {
        return nhanVienServices.addNhanVien(nhanVien);
    }

}
