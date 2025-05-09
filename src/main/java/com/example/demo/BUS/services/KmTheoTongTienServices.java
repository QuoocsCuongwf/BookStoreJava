package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.KmTheoTongTienDAO;
import com.example.demo.model.KmTheoTongTien;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KmTheoTongTienServices {
    private KmTheoTongTienDAO kmTheoTongTienDAO = new KmTheoTongTienDAO();
    private List<KmTheoTongTien> listKmTheoTongTien = new ArrayList<KmTheoTongTien>();
    public List<KmTheoTongTien>getListKmTheoTongTien() {
        listKmTheoTongTien=kmTheoTongTienDAO.getListKmTheoTongTien();
        System.out.println(listKmTheoTongTien);
        return listKmTheoTongTien;
    }
    public List<KmTheoTongTien> findByIDMaChuongTrinh (String idmaChuongTrinh){
        List<KmTheoTongTien> listResult = new ArrayList<KmTheoTongTien>();
        for (KmTheoTongTien khuyenMai : listKmTheoTongTien) {
            if(khuyenMai.getMactkm().contains(idmaChuongTrinh)){
                listResult.add(khuyenMai);
            }
        }
        return listResult;
    }
    public String addKmTheoTongTien(KmTheoTongTien kmTheoTongTien) {
        if(!checkMaChuongTrinh(kmTheoTongTien.getMactkm())){
            kmTheoTongTienDAO.addKmTheoTongTien(kmTheoTongTien);
            return " Add Success";
        }
        return " Add Fail";
    }

    public boolean checkMaChuongTrinh(String mact) {
        for(KmTheoTongTien kmTheoHoaDon : listKmTheoTongTien){
            if(kmTheoHoaDon.getMactkm().equals(mact)){
                return true;
            }
        }
        return false;
    }

    public List<KmTheoTongTien> searchChuongTrinh(String in4ChuongTrinh) {
        List<KmTheoTongTien> result= listKmTheoTongTien.stream()
                .filter(kmTheoTongTien ->
                        String.valueOf(kmTheoTongTien.getMactkm()).contains(in4ChuongTrinh) ||
                               // String.valueOf(khuyenMai.getMasanpham()).contains(in4ChuongTrinh) ||
                                String.valueOf(kmTheoTongTien.getTongtien()).contains(in4ChuongTrinh) ||
                                String.valueOf(kmTheoTongTien.getPhantramkhuyenmai()).contains(in4ChuongTrinh) ||
                                String.valueOf(kmTheoTongTien.getNgayBatDau()).contains(in4ChuongTrinh) ||
                                String.valueOf(kmTheoTongTien.getNgayKetThuc()).contains(in4ChuongTrinh))

                .collect(Collectors.toList());
        return result;
    }

    public void DeleteKmTheoTongTien(String maChuongTrinhKhuyenMai) {
        listKmTheoTongTien.removeIf(kmTheoTongTien -> kmTheoTongTien.getMactkm().equals(maChuongTrinhKhuyenMai));
        kmTheoTongTienDAO.deleteKmTheoTongTien(maChuongTrinhKhuyenMai);
    }
    public String UpdateKmTheoTongTien (KmTheoTongTien kmTheoTongTien) {

        kmTheoTongTienDAO.UpdateKmTheoTongTien(kmTheoTongTien);
        listKmTheoTongTien = kmTheoTongTienDAO.getListKmTheoTongTien();
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
