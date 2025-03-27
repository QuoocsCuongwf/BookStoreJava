package com.example.demo.services;

import com.example.demo.databaseAccesssObject.NhanVienDAO;
import com.example.demo.model.NhanVien;


import java.util.ArrayList;
import java.util.List;

public class NhanVienServices {
    private NhanVienDAO nhanVienDAO=new NhanVienDAO();
    private List<NhanVien> listNhanVien = new ArrayList<>();
    public List<NhanVien> getNhanVienList() {
        listNhanVien=nhanVienDAO.getListNhanVien();
        System.out.println(listNhanVien);
        return listNhanVien;
    }

    public List<NhanVien> findByIdNhanVien(String idnhanVien) {
        List<NhanVien> listResult = new ArrayList<NhanVien>();
        for (NhanVien nhanVien : listNhanVien) {
            if (nhanVien.getManv().contains(idnhanVien)) {
                listResult.add(nhanVien);
            }
        }
        return listResult;
    }

    public void deleteNhanVien(String idnhanVien) {
        for (NhanVien nhanVien : listNhanVien) {
            if (nhanVien.getManv().contains(idnhanVien)) {
            }
        }
    }

    public void addNhanVien(NhanVien nhanVien) {

    }

    public static void main(String[] args) {
        NhanVienServices nhanVienServices=new NhanVienServices();
        nhanVienServices.getNhanVienList();
    }




}
