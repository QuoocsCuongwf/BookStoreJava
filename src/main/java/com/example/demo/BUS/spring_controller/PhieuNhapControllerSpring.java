package com.example.demo.BUS.spring_controller;

import com.example.demo.BUS.services.ChiTietPhieuNhapServices;
import com.example.demo.BUS.services.PhieuNhapServices;
import com.example.demo.model.PhieuNhap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/phieuNhap")
public class PhieuNhapControllerSpring {
    PhieuNhapServices phieuNhapServices=new PhieuNhapServices();

    @GetMapping("/getAllPhieuNhap")
    public List<PhieuNhap> getAllPhieuNhap() {
        System.out.println("PhieuNhapControllerSpring:"+phieuNhapServices.getPhieuNhapList());
        return  phieuNhapServices.getPhieuNhapList();
    }
    @PostMapping("/Search")
    public List<PhieuNhap> timKiem(@RequestParam String find) {
        return phieuNhapServices.searchPhieuNhap(find);
    }
    @PostMapping("/Add")
    public String addPhieuNhap(@RequestBody PhieuNhap p) {
        return phieuNhapServices.addPhieuNhap(p);// return success hoặc fail
    }
    @PostMapping("/Delete")
    public String deletePhieuNhap(@RequestParam String maPhieuNhap) {
         try {
             phieuNhapServices.deletePhieuNhap(maPhieuNhap);
             new ChiTietPhieuNhapServices().deleteList(maPhieuNhap);
             return "success";
         }catch (Exception e){
             System.err.println("PhieuNhapControllerSpring:"+e.getMessage());
             System.out.println("PhieuNhapControllerSpring: LỖI DELETE");
         }
        return "fail";
    }
    @PostMapping("/Update")
    public String updatePhieuNhap(@RequestBody PhieuNhap p) {
        return phieuNhapServices.updatePhieuNhap(p);
    }
}
