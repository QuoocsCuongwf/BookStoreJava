package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.NhanVienDAO;
import com.example.demo.model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class SanPhamServices {
    private List<NhanVien> listNhanVien=new ArrayList<NhanVien>();
    private NhanVienDAO nhanVienDAO;

    public List<NhanVien> getListNhanVien() {
        listNhanVien=nhanVienDAO.getListNhanVien();
        return listNhanVien;
    }
    public String insertNhanVien(NhanVien nhanVien) {
        if(!findByNhanVien(nhanVien.getManv())){

        };
        return "error";
    }
    public boolean findByNhanVien(String maNhanVien) {
        for (NhanVien nhanVien : listNhanVien) {
            if (nhanVien.getManv().equals(maNhanVien)) {
                return true;
            }
        }
        return false;
    }
}
