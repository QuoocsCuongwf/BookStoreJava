package com.example.demo.BUS.spring_controller;


import com.example.demo.BUS.services.Excel;
import com.example.demo.BUS.services.SanPhamServices;
import com.example.demo.model.SanPham;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sanPham")
public class SanPhamControllerSpring {
    SanPhamServices sanPhamServices=new SanPhamServices();
    @GetMapping("/getAllSanPham")
    public List<SanPham> getAllSanPham() {
        System.out.println("add sucess");
        return sanPhamServices.getListSanPham();
    }
    @PostMapping("/search")
    public List<SanPham> search(@RequestParam String find) {
        return sanPhamServices.searchSanPham(find);
    }
    @PostMapping("/update")
    public void updateSanPham(@RequestBody SanPham sanPham) {
        sanPhamServices.updateSanPham(sanPham);
    }
    @PostMapping("/delete")
    public void deleteSanPham(@RequestParam String maSanPham) {
        sanPhamServices.deleteSanPham(maSanPham);
    }
    @PostMapping("/insert")
    public void insertSanPham(@RequestBody SanPham sanPham) {

        try {
            String result = sanPhamServices.insertSanPham(sanPham);
            if ("fail".equals(result)) {
                System.err.println("Failed to insert product. Product already exists.");
                System.err.println("DUYEN : HÀM INSERT TRONG SpringController thành công");
            } else {
                System.out.println("Product inserted successfully.");
                System.err.println("DUYEN : HÀM INSERT TRONG SpringController thành công");
            }
        } catch (Exception e) {
            System.err.println("Error occurred while inserting product: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @PostMapping("/xuatExcel")
    public void xuatExcel() throws IOException {
         Excel.exportSanPhamToExcel();
    }
    @PostMapping("/timKiemTheoKhoangGia")
    public List<SanPham> timKiemTheoKhoangGia(@RequestParam String find, int giaMin, int giaMax) {
        return sanPhamServices.timKiemNangCao(find, giaMin, giaMax);
    }
}
