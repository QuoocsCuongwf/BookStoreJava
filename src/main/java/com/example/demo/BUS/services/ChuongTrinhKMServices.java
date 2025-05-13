package com.example.demo.BUS.services;


import com.example.demo.databaseAccesssObject.ChuongTrinhKMDAO;
import com.example.demo.model.ChuongTrinhKM;
import com.example.demo.model.KmTheoSanPham;
import com.example.demo.model.KmTheoSoTienHoaDon;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChuongTrinhKMServices {
    private static  List<ChuongTrinhKM> listChuongTrinhKM = new ArrayList<ChuongTrinhKM>();
    private static final ChuongTrinhKMDAO chuongTrinhKMDAO = new ChuongTrinhKMDAO();
    public List<ChuongTrinhKM> getAllChuongTrinhKM() {
        listChuongTrinhKM.clear();
        listChuongTrinhKM.addAll(chuongTrinhKMDAO.getAllChuongTrinhKM());
        return listChuongTrinhKM;
    }
    public List<ChuongTrinhKM> findByIDChuongTrinhKhuyenMai (String maChuongTrinhKhuyenMai) {
        List<ChuongTrinhKM> listResult = new ArrayList<ChuongTrinhKM>();
        listChuongTrinhKM = getAllChuongTrinhKM();
        for (ChuongTrinhKM chuongTrinhKM : listChuongTrinhKM) {
            if(chuongTrinhKM.getMactkm().contains(maChuongTrinhKhuyenMai)){
                listResult.add(chuongTrinhKM);
            }
        }
        return listResult;
    }

    public boolean checkMaKhuyenMai(String makm) {
        listChuongTrinhKM = getAllChuongTrinhKM();
        for(ChuongTrinhKM kMai : listChuongTrinhKM){
            if(kMai.getMactkm().equals(makm)){
                return true;
            }
        }
        return false;
    }
    public List<ChuongTrinhKM> searchChuongTrinh(String in4KMai) {
        listChuongTrinhKM = getAllChuongTrinhKM();
        List<ChuongTrinhKM> result= listChuongTrinhKM.stream()
                .filter(ChuongTrinhKM ->
                        String.valueOf(ChuongTrinhKM.getMactkm()).contains(in4KMai) ||
                                String.valueOf(ChuongTrinhKM.getPhantramkhuyenmai()).contains(in4KMai) ||
                                String.valueOf(ChuongTrinhKM.getNgaybd()).contains(in4KMai) ||
                                String.valueOf(ChuongTrinhKM.getNgaykt()).contains(in4KMai))
                .collect(Collectors.toList());
        return result;
    }

}