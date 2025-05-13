package com.example.demo.BUS.spring_controller;

import com.example.demo.BUS.services.ChiTietHoaDonServices;
import com.example.demo.model.HoaDon;
import com.example.demo.BUS.services.HoaDonServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hoaDon")
public class HoaDonControllerSpring {
    HoaDonServices hoaDonServices = new HoaDonServices();

    @GetMapping("/getAllHoaDon")
    public List<HoaDon> getAllHoaDon() {
        return hoaDonServices.getHoaDonList();
    }

    @PostMapping("/timKiem")
    public List<HoaDon> timKiem(@RequestParam String find) {
        return hoaDonServices.searchHoaDon(find);
    }

    @PostMapping("/Delete")
    public String deleteHoaDon(@RequestParam String maHoaDon) {
        hoaDonServices.deleteHoaDon(maHoaDon);
        ChiTietHoaDonServices chiTietHoaDonServices=new ChiTietHoaDonServices();
        chiTietHoaDonServices.deleteList(maHoaDon);
        return "success";
    }

    @PostMapping("/Add")
    public String addHoaDon(@RequestBody HoaDon hoaDon) {
        return hoaDonServices.addHoaDon(hoaDon);
    }

    @PostMapping("/Update")
    public String updateHoaDon(@RequestBody HoaDon hoaDon) {
        return hoaDonServices.updateHoaDon(hoaDon);
    }
}
