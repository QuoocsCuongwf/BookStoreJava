package com.example.demo.BUS.spring_controller;

import com.example.demo.BUS.services.ChuongTrinhKMServices;
import com.example.demo.model.ChuongTrinhKM;
import com.example.demo.model.KmTheoTongTien;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("ChuonngTrinhKM")
public class ChuongTrinhKMControllerSpring {

    private final ChuongTrinhKMServices chuongTrinhKMServices = new ChuongTrinhKMServices();
    @GetMapping("/getAllChuongTrinhKM")
    public List<ChuongTrinhKM> getAllChuongTrinhKM() {
        return chuongTrinhKMServices.getAllChuongTrinhKM();
//    }
//    @PostMapping("/timkiem")
//    public List<ChuongTrinhKM> timKiem (@RequestParam String timkiem) {
//        return chuongTrinhKMServices.searchChuongTrinh(timkiem);
//    }
//    @PostMapping("/Add")
//    public String AddChuongTrinh(@RequestBody ChuongTrinhKM chuongTrinhKhuyenMai)
//    {return chuongTrinhKMServices.addChuongTrinhKhuyenMai(chuongTrinhKhuyenMai);}
//
//    @PostMapping("Update")
//    public String UpdateChuongTrinhKM(@RequestBody ChuongTrinhKM chuongTrinhKM)
//    { return chuongTrinhKMServices.updateChuongTrinhKhuyenMai(chuongTrinhKM); }


    }}
