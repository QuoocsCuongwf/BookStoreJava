package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.KhuyenMaiDAO;
import com.example.demo.model.KmTheoHoaDon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KhuyenMaiServices {
    private KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
    private List<KmTheoHoaDon> listKhuyenMai = new ArrayList<KmTheoHoaDon>();
    public List<KmTheoHoaDon>getKhuyenMaiList() {
        listKhuyenMai=khuyenMaiDAO.getListKhuyenMai();
        System.out.println(listKhuyenMai);
        return listKhuyenMai;
    }
    public List<KmTheoHoaDon> findByIDMaChuongTrinh (String idmaChuongTrinh){
        List<KmTheoHoaDon> listResult = new ArrayList<KmTheoHoaDon>();
        for (KmTheoHoaDon khuyenMai : listKhuyenMai) {
            if(khuyenMai.getMactkm().contains(idmaChuongTrinh)){
                listResult.add(khuyenMai);
            }
        }
        return listResult;
    }
    public String addKhuyenMai(KmTheoHoaDon khuyenMai) {
        if(!checkMaChuongTrinh(khuyenMai.getMactkm())){
            khuyenMaiDAO.addKhuyenMai(khuyenMai);
            return " Add Success";
        }
        return " Add Fail";
    }

    public boolean checkMaChuongTrinh(String mact) {
        for(KmTheoHoaDon kmTheoHoaDon : listKhuyenMai){
            if(kmTheoHoaDon.getMactkm().equals(mact)){
                return true;
            }
        }
        return false;
    }

    public List<KmTheoHoaDon> searchChuongTrinh(String in4ChuongTrinh) {
        List<KmTheoHoaDon> result= listKhuyenMai.stream()
                .filter(khuyenMai ->
                        String.valueOf(khuyenMai.getMactkm()).contains(in4ChuongTrinh) ||
                                String.valueOf(khuyenMai.getMasanpham()).contains(in4ChuongTrinh) ||
                                String.valueOf(khuyenMai.getTongtien()).contains(in4ChuongTrinh) ||
                                String.valueOf(khuyenMai.getPhantramkhuyenmai()).contains(in4ChuongTrinh)
                )
                .collect(Collectors.toList());
        return result;
    }

    public void DeleteKhuyenMai(String maChuongTrinhKhuyenMai) {
        listKhuyenMai.removeIf(khuyenMai -> khuyenMai.getMactkm().equals(maChuongTrinhKhuyenMai));
        khuyenMaiDAO.deleteKhuyenMai(maChuongTrinhKhuyenMai);
    }
    public String UpdateKhuyenMai (KmTheoHoaDon khuyenMai) {
        khuyenMaiDAO.updateChuongTrinhKhuyenMai(khuyenMai);
        return "update success";
    }

//    public static void main(String[] args){
//        KmTheoHoaDonServices kmTheoHoaDonServices=new KmTheoHoaDonServices();
//        KmTheoHoaDon kmTheoHoaDon = new KmTheoHoaDon();
//        kmTheoHoaDon.setMactkm("CTKM001");
//        kmTheoHoaDon.setTongtien(300.000);
//        kmTheoHoaDon.setPhantramkhuyenmai(0.2);
//
//
//        kmTheoHoaDonServices.addKhuyenMai(kmTheoHoaDon);
//        kmTheoHoaDonServices.getKmTheoHoaDonList();
//
//
//    }
}
