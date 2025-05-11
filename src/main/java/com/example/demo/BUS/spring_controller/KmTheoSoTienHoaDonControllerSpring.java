package com.example.demo.BUS.spring_controller;

import com.example.demo.BUS.services.KmTheoSoTienHoaDonServices;
import com.example.demo.model.KmTheoSoTienHoaDon;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("KhuyenMaiTongTien")
public class KmTheoSoTienHoaDonControllerSpring {
    private static List<KmTheoSoTienHoaDon> list = new ArrayList<KmTheoSoTienHoaDon>();
    private final KmTheoSoTienHoaDonServices Services = new KmTheoSoTienHoaDonServices();
    @GetMapping("/getAll")
    public List<KmTheoSoTienHoaDon> getAllKmTheoSoTienHoaDon(){
        list = Services.getKmTheoSoTienHoaDonList();
        return list;
    }
    @PostMapping("/Delete")
    public String DeleteKmTheoSoTienHoaDon(@RequestParam String maChuongTrinh) {
        Services.DeleteKmTheoSoTienHoaDon(maChuongTrinh);
        return "delete success";
    }
    @PostMapping("/Add")
    public String AddKhuyenMai(@RequestBody KmTheoSoTienHoaDon khuyenMai) {return Services.addKmTheoSoTienHoaDon(khuyenMai);}

    @PostMapping("/Update")
    public String updateKhuyenMai(@RequestBody KmTheoSoTienHoaDon khuyenMai) { return Services.UpdateKmTheoSoTienHoaDon(khuyenMai); }
    @PostMapping("/search")
    public List<KmTheoSoTienHoaDon> searchListKMTheoSoTienHoaDon(@RequestParam String find) {
        return Services.searchChuongTrinh(find);
    }

}