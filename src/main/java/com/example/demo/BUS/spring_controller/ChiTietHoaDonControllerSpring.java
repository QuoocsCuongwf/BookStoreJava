package com.example.demo.BUS.spring_controller;

import com.example.demo.BUS.services.ChiTietHoaDonServices;
import com.example.demo.BUS.services.HoaDonServices;
import com.example.demo.BUS.services.MomoPayment;
import com.example.demo.model.ChiTietHoaDon;
import com.example.demo.model.HoaDon;
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

    @PostMapping("/getByMaHoaDon")
    public List<ChiTietHoaDon> getByMaHoaDon(@RequestParam String maHoaDon) {
        return chiTietHoaDonServices.getList(maHoaDon);
    }

    @PostMapping("/add")
    public void addChiTietHoaDon(@RequestBody List<ChiTietHoaDon> listChiTietHoaDon) throws Exception {
        chiTietHoaDonServices.addChiTietHoaDon(listChiTietHoaDon);
    }
    @PostMapping("/check")
    public int checkChiTietHoaDon(@RequestBody ChiTietHoaDon chiTietHoaDon) {
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
    @PostMapping("/deleteAllCTHD")
    public String deleteChiTietHoaDon(@RequestParam String maHoaDon) {
        chiTietHoaDonServices.deleteList(maHoaDon);
        return "success";
    }
    @PostMapping("/momoPayment")
    public String momoPayment(@RequestParam String maHoaDon) {
        return MomoPayment.CreateQRCode(maHoaDon);
    }
}
