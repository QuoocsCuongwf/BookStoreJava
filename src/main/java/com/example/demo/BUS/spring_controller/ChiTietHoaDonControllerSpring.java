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
    public void addChiTietHoaDon(@RequestBody ChiTietHoaDon chiTietHoaDon) {
        System.out.println(chiTietHoaDon);
        chiTietHoaDonServices.addChiTietHoaDon(chiTietHoaDon);
    }
    @PostMapping("/check")
    public int checkChiTietHoaDon(@RequestBody ChiTietHoaDon chiTietHoaDon) {
        System.out.println(chiTietHoaDon);
        return chiTietHoaDonServices.checkChiTietHoaDon(chiTietHoaDon);
    }

    @PostMapping("/update")
    public int updateChiTietHoaDon(@RequestBody List<ChiTietHoaDon> listChiTietHoaDon) {
        return chiTietHoaDonServices.updateList(listChiTietHoaDon.get(0),listChiTietHoaDon.get(1));
    }

    @PostMapping("/delete")
    public String deleteChiTietHoaDon(@RequestParam String maHoaDon, @RequestParam String maSanPham) {
        chiTietHoaDonServices.deleteList(maHoaDon, maSanPham);
        return "success";
    }
}
