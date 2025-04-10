package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.NhanVienDAO;
import com.example.demo.model.NhanVien;


import java.time.LocalDate;
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


    public String addNhanVien(NhanVien nhanVien) {
        if (!checkMaNhanVien(nhanVien.getManv())){
            nhanVienDAO.addNhanVien(nhanVien);
            return "Add Success";
        }
        return "Add Fail";
    }

    public boolean checkMaNhanVien(String idnhanVien) {
        for (NhanVien nhanVien : listNhanVien) {
            if (nhanVien.getManv().equals(idnhanVien)) {
                return true;
            }
        }
        return false;
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

    public String updateNhanVien(NhanVien nhanVien) {
        nhanVienDAO.updateNhanVien(nhanVien);
        return "update Success";
    }

    public static void main(String[] args) {
        NhanVienServices nhanVienServices=new NhanVienServices();
        NhanVien nhanVien=new NhanVien();
        nhanVien.setManv("NV001");
        nhanVien.setHonv("Nguyen");
        nhanVien.setTennv("Van A");
        nhanVien.setCccd("123456789");
        nhanVien.setChucvu("Nhân viên");
        nhanVien.setMail("vana@example.com");
        nhanVien.setNgayvaolam(LocalDate.of(2024, 1, 1)); // nếu kiểu LocalDate
        nhanVien.setLuong(5000000);

        nhanVienServices.addNhanVien(nhanVien);
    }




}
