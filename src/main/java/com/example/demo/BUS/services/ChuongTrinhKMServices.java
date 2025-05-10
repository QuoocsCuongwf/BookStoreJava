package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.ChuongTrinhKMDAO;
import com.example.demo.model.ChuongTrinhKM;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChuongTrinhKMServices {
    private ChuongTrinhKMDAO chuongTrinhKMDAO  = new ChuongTrinhKMDAO();
    private List<ChuongTrinhKM> listChuongTrinhKM = new ArrayList<ChuongTrinhKM>();
    public List<ChuongTrinhKM>getChuongTrinhKMList() {
        listChuongTrinhKM=chuongTrinhKMDAO.getListChuongTrinhKM();
        System.out.println(listChuongTrinhKM);
        return listChuongTrinhKM;
    }
    public List<ChuongTrinhKM> findByIDChuongTrinhKhuyenMai (String idkMai){
        List<ChuongTrinhKM> listResult = new ArrayList<ChuongTrinhKM>();
        for (ChuongTrinhKM chuongTrinhKM : listChuongTrinhKM) {
            if(chuongTrinhKM.getMactkm().contains(idkMai)){
                listResult.add(chuongTrinhKM);
            }
        }
        return listResult;
    }


    public String addChuongTrinhKhuyenMai(ChuongTrinhKM kMai) {
        if(!checkMaKhuyenMai(kMai.getMactkm())){
            chuongTrinhKMDAO.addChuongTrinhKhuyenMai(kMai);
            return " Add Success";
        }
        return " Add Fail";
    }

    public boolean checkMaKhuyenMai(String makm) {
        for(ChuongTrinhKM kMai : listChuongTrinhKM){
            if(kMai.getMactkm().equals(makm)){
                return true;
            }
        }
        return false;
    }

    public List<ChuongTrinhKM> searchChuongTrinh(String in4KMai) {
        List<ChuongTrinhKM> result= listChuongTrinhKM.stream()
                .filter(ChuongTrinhKM ->
                        String.valueOf(ChuongTrinhKM.getMactkm()).contains(in4KMai) ||
                                String.valueOf(ChuongTrinhKM.getPhantramkhuyenmai()).contains(in4KMai) ||
                                String.valueOf(ChuongTrinhKM.getNgayBatDau()).contains(in4KMai) ||
                                String.valueOf(ChuongTrinhKM.getNgayKetThuc()).contains(in4KMai))

                .collect(Collectors.toList());
        return result;
    }
    public void deleteChuongTrinhKhuyenMai(String maKmai) {
        listChuongTrinhKM.removeIf(ChuongTrinhKM -> ChuongTrinhKM.getMactkm().equals(maKmai));
        chuongTrinhKMDAO.deleteChuongTrinhKhuyenMai(maKmai);
    }
    public String updateChuongTrinhKhuyenMai(ChuongTrinhKM kMai) {
        chuongTrinhKMDAO.UpdateChuongTrinhKhuyenMai(kMai);
        listChuongTrinhKM = chuongTrinhKMDAO.getListChuongTrinhKM(); // Cập nhật danh sách từ DB
        return "update success";
    }
}
