package com.example.demo.BUS.spring_controller;


import com.example.demo.BUS.services.KmTheoSanPhamServices;
import com.example.demo.model.KmTheoSanPham;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("KmTheoSanPham")
public class KmTheoSanPhamControllerSpring {
    private final KmTheoSanPhamServices kmTheoSanPhamServices = new KmTheoSanPhamServices();
    @GetMapping("/getAllKhuyenMai")
    public List<KmTheoSanPham> AllKhuyenMai() {
        System.out.println(kmTheoSanPhamServices.getKmTheoSanPhamList());
        return kmTheoSanPhamServices.getKmTheoSanPhamList();
    }

    @PostMapping("/timKiem")
    public  List<KmTheoSanPham> timKiem(@RequestParam String find){
        return kmTheoSanPhamServices.searchChuongTrinh(find);
    }
    @PostMapping("/Delete")
    public String DeleteKhuyenMai(@RequestParam String maKmai) {
        kmTheoSanPhamServices.deleteKmTheoSanPham(maKmai);
        return "delete success";
    }
    @PostMapping("/Add")
    public String AddKhuyenMai(@RequestBody KmTheoSanPham kmTheoSanPham) {return kmTheoSanPhamServices.addKmTheoSanPham(kmTheoSanPham);}

    @PostMapping("/Update")
    public String updateKhuyenMai(@RequestBody KmTheoSanPham kmTheoSanPham) { return kmTheoSanPhamServices.updateKmTheoSanPham(kmTheoSanPham); }



}
