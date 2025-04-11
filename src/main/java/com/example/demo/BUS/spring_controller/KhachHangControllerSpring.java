package com.example.demo.spring_controller;

import com.example.demo.model.KhachHang;
import com.example.demo.model.NhanVien;
import com.example.demo.services.KhachHangServices;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

//xong
import java.util.List;
@RestController
@RequestMapping("KhachHang")
public class KhachHangControllerSpring {
    KhachHangServices khachHangServices = new KhachHangServices();
    @GetMapping("/getAllKhachHang")
    public List<KhachHang> getKhachHangList(){
        System.out.println(khachHangServices.getListKhachHang());
        return khachHangServices.getListKhachHang();
    }
    @PostMapping("/timKiemKhachHang")
    public List<KhachHang> timKiem( @RequestParam String timkiem){
        return khachHangServices.searchKhachHang(timkiem);
    }
    @PostMapping("/deleteKhachHang")
    public String DeleteKhachHang(@RequestParam String khachHang){
        khachHangServices.deleteKhachHang(khachHang);
        return "success";
    }
    @PostMapping("/addKhachHang")
    public String  addKhachHang(@RequestBody KhachHang khachHang){
        return khachHangServices.addKhachHang(khachHang);
    }
}
