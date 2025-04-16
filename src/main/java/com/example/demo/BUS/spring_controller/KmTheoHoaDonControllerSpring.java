package com.example.demo.BUS.spring_controller;

import com.example.demo.BUS.services.TacGiaServices;
import com.example.demo.BUS.services.KmTheoHoaDonServices;
import com.example.demo.model.KmTheoHoaDon;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("KmTheoHoaDon")

public class KmTheoHoaDonControllerSpring {

    KmTheoHoaDonServices kmTheoHoaDonServices = new KmTheoHoaDonServices();
    @GetMapping("/getAllKmTheoHoaDon")
    public List<KmTheoHoaDon> AllKmTheoHoaDon() {
        System.out.println(kmTheoHoaDonServices.getKmTheoHoaDonList());
        return kmTheoHoaDonServices.getKmTheoHoaDonList();
    }

    @PostMapping("/timKiem")
    public  List<KmTheoHoaDon> timKiem(@RequestParam String find){
        return kmTheoHoaDonServices.searchChuongTrinh(find);
    }
    @PostMapping("/Delete")
    public String DeleteMaChuongTrinhKhuyenMai(@RequestParam String maChuongTrinh) {
        kmTheoHoaDonServices.deleteKhuyenMai(maChuongTrinh);
        return "delete success";
    }
    @PostMapping("/Add")
    public String AddKhuyenMai(@RequestBody KmTheoHoaDon kmTheoHoaDon) {return kmTheoHoaDonServices.addKhuyenMai(kmTheoHoaDon);};
    @PostMapping("/Update")
    public String UpdateChuongTrinhKhuyenMai(@RequestBody KmTheoHoaDon kmTheoHoaDon) { return kmTheoHoaDonServices.updateChuongTrinhKhuyenMai(kmTheoHoaDon); }


}
