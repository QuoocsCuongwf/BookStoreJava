package com.example.demo.BUS.spring_controller;

import com.example.demo.BUS.services.ChuongTrinhKMServices;
import com.example.demo.BUS.services.KmTheoSoTienHoaDonServices;
import com.example.demo.model.ChuongTrinhKM;
import com.example.demo.model.KmTheoSoTienHoaDon;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("ChuongTrinhKM")
public class ChuongTrinhKMControllerSpring {
    private final ChuongTrinhKMServices chuongTrinhKMServices = new ChuongTrinhKMServices();
    @GetMapping("/getAllChuongTrinhKM")
    public List<ChuongTrinhKM> getAllChuongTrinhKM() {
        return chuongTrinhKMServices.getAllChuongTrinhKM();
    }
    @PostMapping("/timKiem")
    public List<ChuongTrinhKM> timKiem(@RequestParam String find) {
        return chuongTrinhKMServices.searchChuongTrinh(find);
    }
    @PostMapping("/checkKieuKhuyenMai")
    public String checkChuongTrinh(@RequestBody String find) {
        KmTheoSoTienHoaDonServices services = new KmTheoSoTienHoaDonServices();
        if (services.checkMaChuongTrinh(find))// nếu tìm thấy trong bảng kmhd thì true
        {
            return "hd";
        }
        return "sp";
    }
}