package com.example.demo.BUS.spring_controller;

import com.example.demo.BUS.services.NhaXuatBanServices;
import com.example.demo.model.NhaXuatBan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nhaXuatBan")
public class NhaXuatBanControllerSpring {
    NhaXuatBanServices nhaXuatBanServices = new NhaXuatBanServices();

    @GetMapping("/getAllNhaXuatBan")
    public List<NhaXuatBan> getAllNhaXuatBan() {
        System.out.println(nhaXuatBanServices.getListNhaXuatBan());
        return nhaXuatBanServices.getListNhaXuatBan();
    }

    @PostMapping("/timKiem")
    public List<NhaXuatBan> timKiem(@RequestParam String find) {
        return nhaXuatBanServices.searchNhaXuatBan(find);
    }

    @PostMapping("/Delete")
    public String deleteNhaXuatBan(@RequestParam String maNhaXuatBan) {
        nhaXuatBanServices.deleteNhaXuatBan(maNhaXuatBan);
        return "Success";
    }

    @PostMapping("/Add")
    public String addNhaXuatBan(@RequestBody NhaXuatBan nhaXuatBan) {
        return nhaXuatBanServices.addNhaXuatBan(nhaXuatBan);
    }

    @PostMapping("/Update")
    public String updateNhaXuatBan(@RequestBody NhaXuatBan nhaXuatBan) {
        return nhaXuatBanServices.updateNhaXuatBan(nhaXuatBan);
    }
}