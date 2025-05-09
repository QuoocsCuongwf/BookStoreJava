package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.ChuongTrinhKhuyenMaiDAO;
import com.example.demo.model.ChuongTrinhKhuyenMai;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChuongTrinhKhuyenMaiServices {
    private ChuongTrinhKhuyenMaiDAO chuongTrinhKhuyenMaiDAO  = new ChuongTrinhKhuyenMaiDAO();
    private List<ChuongTrinhKhuyenMai> listChuongTrinhKhuyenMai = new ArrayList<ChuongTrinhKhuyenMai>();
    public List<ChuongTrinhKhuyenMai>getChuongTrinhKhuyenMaiList() {
        listChuongTrinhKhuyenMai=chuongTrinhKhuyenMaiDAO.getListChuongTrinhKhuyenMai();
        System.out.println(listChuongTrinhKhuyenMai);
        return listChuongTrinhKhuyenMai;
    }
    public List<ChuongTrinhKhuyenMai> findByIDChuongTrinhKhuyenMai (String idkMai){
        List<ChuongTrinhKhuyenMai> listResult = new ArrayList<ChuongTrinhKhuyenMai>();
        for (ChuongTrinhKhuyenMai chuongTrinhKhuyenMai : listChuongTrinhKhuyenMai) {
            if(chuongTrinhKhuyenMai.getMactkm().contains(idkMai)){
                listResult.add(chuongTrinhKhuyenMai);
            }
        }
        return listResult;
    }


    public String addChuongTrinhKhuyenMai(ChuongTrinhKhuyenMai kMai) {
        if(!checkMaKhuyenMai(kMai.getMactkm())){
            chuongTrinhKhuyenMaiDAO.addChuongTrinhKhuyenMai(kMai);
            return " Add Success";
        }
        return " Add Fail";
    }

    public boolean checkMaKhuyenMai(String makm) {
        for(ChuongTrinhKhuyenMai kMai : listChuongTrinhKhuyenMai){
            if(kMai.getMactkm().equals(makm)){
                return true;
            }
        }
        return false;
    }

    public List<ChuongTrinhKhuyenMai> searchChuongTrinh(String in4KMai) {
        List<ChuongTrinhKhuyenMai> result= listChuongTrinhKhuyenMai.stream()
                .filter(ChuongTrinhKhuyenMai ->
                        String.valueOf(ChuongTrinhKhuyenMai.getMactkm()).contains(in4KMai) ||
                                String.valueOf(ChuongTrinhKhuyenMai.getPhantramkhuyenmai()).contains(in4KMai) ||
                                String.valueOf(ChuongTrinhKhuyenMai.getNgayBatDau()).contains(in4KMai) ||
                                String.valueOf(ChuongTrinhKhuyenMai.getNgayKetThuc()).contains(in4KMai))

                .collect(Collectors.toList());
        return result;
    }
    public void deleteChuongTrinhKhuyenMai(String maKmai) {
        listChuongTrinhKhuyenMai.removeIf(ChuongTrinhKhuyenMai -> ChuongTrinhKhuyenMai.getMactkm().equals(maKmai));
        chuongTrinhKhuyenMaiDAO.deleteChuongTrinhKhuyenMai(maKmai);
    }
    public String updateChuongTrinhKhuyenMai(ChuongTrinhKhuyenMai kMai) {
        chuongTrinhKhuyenMaiDAO.UpdateChuongTrinhKhuyenMai(kMai);
        listChuongTrinhKhuyenMai = chuongTrinhKhuyenMaiDAO.getListChuongTrinhKhuyenMai(); // Cập nhật danh sách từ DB
        return "update success";
    }
}
