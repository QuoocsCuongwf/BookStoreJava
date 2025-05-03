package com.example.demo.BUS.spring_controller;

import com.example.demo.model.KhachHang;
import com.example.demo.model.NhanVien;
import com.example.demo.BUS.services.KhachHangServices;
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
    public List<KhachHang> timKiem( @RequestParam String find){
        return khachHangServices.searchKhachHang(find);
    }
    @PostMapping("/deleteKhachHang")
    public String DeleteKhachHang(@RequestParam String maKhachHang){ // tham số phải trùng với key truyền vào trong callPost... kkhi gọi ở controller
        khachHangServices.deleteKhachHang(maKhachHang);
        return "success";
    }
    @PostMapping("/addKhachHang")
    public String  addKhachHang(@RequestBody KhachHang khachHang){
        return khachHangServices.addKhachHang(khachHang);
    }

    @PostMapping("/updateKhachHang")
    public String updateKhachHang(@RequestBody KhachHang khachHang){
       return khachHangServices.updateKhachHang(khachHang);
    }
}