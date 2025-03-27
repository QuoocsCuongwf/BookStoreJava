package com.example.demo.services;

import com.example.demo.Reponsitory;
import com.example.demo.model.NhanVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class NhanVienServices {
    @Autowired
    private Reponsitory repo;
    private List<NhanVien> listNhanVien = new ArrayList<>();
    public List<NhanVien> getNhanVienList() {
        listNhanVien = repo.findAll();
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
                repo.delete(nhanVien);
            }
        }
    }

    public void addNhanVien(NhanVien nhanVien) {
        repo.save(nhanVien);
    }




}
