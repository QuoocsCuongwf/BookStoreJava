package com.example.demo.BUS.spring_controller;

import com.example.demo.BUS.services.KmTheoTongTienServices;
import com.example.demo.model.KmTheoTongTien;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("KhuyenMai")

public class KmTheoTongTienControllerSpring {

    private final KmTheoTongTienServices kmTheoTongTienServices = new KmTheoTongTienServices();
    @GetMapping("/getAllKhuyenMai")
    public List<KmTheoTongTien> AllKhuyenMai() {
        System.out.println(kmTheoTongTienServices.getKmTheoTongTienList());
        return kmTheoTongTienServices.getKmTheoTongTienList();
    }

    @PostMapping("/timKiem")
    public  List<KmTheoTongTien> timKiem(@RequestParam String find){
        return KmTheoTongTienServices.searchChuongTrinh(find);
    }
    @PostMapping("/Delete")
    public String DeleteKmTheoTongTien(@RequestParam String maChuongTrinh) {
        KmTheoTongTienServices.DeleteKmTheoTongTien(maChuongTrinh);
        return "delete success";
    }
    @PostMapping("/Add")
    public String AddKhuyenMai(@RequestBody KmTheoTongTien khuyenMai) {return kmTheoTongTienServices.addKmTheoTongTien(khuyenMai);}

    @PostMapping("/Update")
    public String updateKhuyenMai(@RequestBody KmTheoTongTien khuyenMai) { return kmTheoTongTienServices.UpdateKmTheoTongTien(khuyenMai); }


}
