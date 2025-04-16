package com.example.demo.BUS.spring_controller;

import com.example.demo.BUS.services.ChiTietHoaDonServices;
import com.example.demo.model.ChiTietHoaDon;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chiTietHoaDon")
public class ChiTietHoaDonControllerSpring {
    ChiTietHoaDonServices chiTietHoaDonServices = new ChiTietHoaDonServices();

    @GetMapping("/getAll")
    public List<ChiTietHoaDon> getAll() {
        return chiTietHoaDonServices.getList();
    }

    @GetMapping("/getByMaHoaDon")
    public List<ChiTietHoaDon> getByMaHoaDon(@RequestParam String maHoaDon) {
        return chiTietHoaDonServices.getList(maHoaDon);
    }

    @PostMapping("/add")
    public String addChiTietHoaDon(@RequestBody ChiTietHoaDon chiTietHoaDon) {
        chiTietHoaDonServices.addChiTietHoaDon(chiTietHoaDon);
        return "success";
    }

    @PostMapping("/update")
    public String updateChiTietHoaDon(@RequestBody ChiTietHoaDon chiTietHoaDon) {
        chiTietHoaDonServices.updateList(chiTietHoaDon);
        return "success";
    }

    @PostMapping("/delete")
    public String deleteChiTietHoaDon(@RequestParam String maHoaDon, @RequestParam String maSanPham) {
        chiTietHoaDonServices.deleteList(maHoaDon, maSanPham);
        return "success";
    }
}
