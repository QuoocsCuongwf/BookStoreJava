package com.example.demo.services;

import com.example.demo.databaseAccesssObject.NhanVienDAO;
import com.example.demo.model.NhanVien;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    public void addNhanVien(NhanVien nhanVien) {

    }

    public List<NhanVien> searchNhanVien(String inforNhanVien) {
        List<NhanVien> result=listNhanVien.stream()
                .filter(nhanVien -> nhanVien.getManv().contains(inforNhanVien) || nhanVien.getHonv().contains(inforNhanVien) || nhanVien.getTennv().contains(inforNhanVien))
                .collect(Collectors.toList());
        return result;
    }

    public void deleteNhanVien(String maNhanVien) {
        listNhanVien.removeIf(nv->nv.getManv().equals(maNhanVien));
        nhanVienDAO.deleteNhanVien(maNhanVien);
    }

    public static void main(String[] args) {
        NhanVienServices nhanVienServices=new NhanVienServices();
        nhanVienServices.deleteNhanVien("NV001");
    }




}
