package com.example.demo.BUS.spring_controller;

import com.example.demo.BUS.services.ChiTietPhieuNhapServices;
import com.example.demo.model.ChiTietHoaDon;
import com.example.demo.model.ChiTietPhieuNhap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/ChiTietPhieuNhap")
public class ChiTietPhieuNhapControllerSpring {
    ChiTietPhieuNhapServices chiTietPhieuNhapServices = new ChiTietPhieuNhapServices();

    @GetMapping("/getAll")
    public List<ChiTietPhieuNhap> getAll(){
        return chiTietPhieuNhapServices.getList();
    }
    @PostMapping("/getByMaPhieuNhap")
    public List<ChiTietPhieuNhap> getByMaPhieuNhap(@RequestParam String find){
        return chiTietPhieuNhapServices.getList(find);
    }
    @PostMapping("/Add")
    public String addChiTietPhieuNhap(@RequestBody List<ChiTietPhieuNhap> listThem) {
        chiTietPhieuNhapServices.addChiTietPhieuNhap(listThem);
        return "success";
    }
    @PostMapping("/check")
    public String checkChiTietPhieuNhap(@RequestBody ChiTietPhieuNhap chiTietPhieuNhap) {
        return chiTietPhieuNhapServices.checkChiTietPhieuNhap(chiTietPhieuNhap);
    }

    @PostMapping("/update")
    public  String updateChiTietPhieuNhap(@RequestBody List<ChiTietPhieuNhap> chiTietPhieuNhapList) {
        return chiTietPhieuNhapServices.updateList(chiTietPhieuNhapList.get(0),chiTietPhieuNhapList.get(1));
    }

    @PostMapping("/delete")
    public String deleteChiTietPhieuNhap(@RequestParam String maPhieuNhap, @RequestParam String maSanPham) {
        chiTietPhieuNhapServices.deleteList(maPhieuNhap, maSanPham);
        return "success";
    }
    @PostMapping("/deleteAllCTPN")
    public String deleteChiTietHoaDon(@RequestParam String maPhieuNhap) {
        chiTietPhieuNhapServices.deleteList(maPhieuNhap);
        return "success";

    }
}
