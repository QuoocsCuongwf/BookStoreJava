package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.SanPhamDAO;
import com.example.demo.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamServices {
    private List<SanPham> listSanPham=new ArrayList<SanPham>();
    private SanPhamDAO sanPhamDAO=new SanPhamDAO();

    public List<SanPham> getListSanPham() {
        listSanPham=sanPhamDAO.getListSanPham();
        return listSanPham;
    }
    public String insertSanPham(SanPham nhanVien) {
        if(!findBySanPham(nhanVien.getMasp())){

        };
        return "error";
    }
    public boolean findBySanPham(String maSanPham) {
        for (SanPham nhanVien : listSanPham) {
            if (nhanVien.getMasp().equals(maSanPham)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SanPhamServices sanPhamServices=new SanPhamServices();
        System.out.println("list San Pham");
        sanPhamServices.getListSanPham();
    }
}
