package com.example.demo.BUS.spring_controller;

import com.example.demo.BUS.services.KhuyenMaiServices;
import com.example.demo.model.KmTheoHoaDon;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("KhuyenMai")

public class KhuyenMaiControllerSpring {

    private final KhuyenMaiServices khuyenMaiServices = new KhuyenMaiServices();
    @GetMapping("/getAllKhuyenMai")
    public List<KmTheoHoaDon> AllKhuyenMai() {
        System.out.println(khuyenMaiServices.getKhuyenMaiList());
        return khuyenMaiServices.getKhuyenMaiList();
    }

    @PostMapping("/timKiem")
    public  List<KmTheoHoaDon> timKiem(@RequestParam String find){
        return khuyenMaiServices.searchChuongTrinh(find);
    }
    @PostMapping("/Delete")
    public String DeleteKhuyenMai(@RequestParam String maChuongTrinh) {
        khuyenMaiServices.DeleteKhuyenMai(maChuongTrinh);
        return "delete success";
    }
    @PostMapping("/Add")
    public String AddKhuyenMai(@RequestBody KmTheoHoaDon khuyenMai) {return khuyenMaiServices.addKhuyenMai(khuyenMai);};
    @PostMapping("/Update")
    public String UpdateKhuyenMai(@RequestBody KmTheoHoaDon khuyenMai) { return khuyenMaiServices.UpdateKhuyenMai(khuyenMai); }


}
