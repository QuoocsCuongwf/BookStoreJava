package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.KhuyenMaiDAO;
import com.example.demo.model.KhuyenMai;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KhuyenMaiServices {
    private KhuyenMaiDAO khuyenMaiDAO = new KhuyenMaiDAO();
    private List<KhuyenMai> listKhuyenMai = new ArrayList<KhuyenMai>();
    public List<KhuyenMai>getKhuyenMaiList() {
        listKhuyenMai=khuyenMaiDAO.getListKhuyenMai();
        System.out.println(listKhuyenMai);
        return listKhuyenMai;
    }
    public List<KhuyenMai> findByIDMaChuongTrinh (String idmaChuongTrinh){
        List<KhuyenMai> listResult = new ArrayList<KhuyenMai>();
        for (KhuyenMai khuyenMai : listKhuyenMai) {
            if(khuyenMai.getMactkm().contains(idmaChuongTrinh)){
                listResult.add(khuyenMai);
            }
        }
        return listResult;
    }
    public String addKhuyenMai(KhuyenMai khuyenMai) {
        if(!checkMaChuongTrinh(khuyenMai.getMactkm())){
            khuyenMaiDAO.addKhuyenMai(khuyenMai);
            return " Add Success";
        }
        return " Add Fail";
    }

    public boolean checkMaChuongTrinh(String mact) {
        for(KhuyenMai kmTheoHoaDon : listKhuyenMai){
            if(kmTheoHoaDon.getMactkm().equals(mact)){
                return true;
            }
        }
        return false;
    }

    public List<KhuyenMai> searchChuongTrinh(String in4ChuongTrinh) {
        List<KhuyenMai> result= listKhuyenMai.stream()
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
    public String UpdateKhuyenMai (KhuyenMai khuyenMai) {
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
