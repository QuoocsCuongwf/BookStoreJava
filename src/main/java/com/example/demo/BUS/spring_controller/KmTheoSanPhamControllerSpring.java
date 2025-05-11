package com.example.demo.BUS.spring_controller;


import com.example.demo.BUS.services.KmTheoSanPhamServices;
import com.example.demo.model.KmTheoSanPham;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("KmTheoSanPham")
public class KmTheoSanPhamControllerSpring {
    private final KmTheoSanPhamServices Services = new KmTheoSanPhamServices();
    private static List<KmTheoSanPham> kmTheoSanPham = new ArrayList<KmTheoSanPham>();
    @GetMapping("/getAll")
    public List<KmTheoSanPham> getAllKmTheoSanPham(){
        kmTheoSanPham= Services.getKmTheoSanPhamList();
        return kmTheoSanPham;
    }
    @PostMapping("/Delete")
    public String DeleteKhuyenMai(@RequestParam String find) {
        Services.deleteKmTheoSanPham(find);
        return "delete success";
    }
    @PostMapping("/Add")
    public String AddKhuyenMai(@RequestBody KmTheoSanPham kmTheoSanPham) {return Services.addKmTheoSanPham(kmTheoSanPham);}

    @PostMapping("/Update")
    public String updateKhuyenMai(@RequestBody KmTheoSanPham kmTheoSanPham) { return Services.updateKmTheoSanPham(kmTheoSanPham); }

    @PostMapping("/searchKmTheoSanPham")
    public List<KmTheoSanPham> searchKMTheoSanPham(@RequestParam String find){
        return Services.searchChuongTrinh(find);
    }


}