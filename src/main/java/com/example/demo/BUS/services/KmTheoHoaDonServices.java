package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.KmTheoHoaDonDAO;
import com.example.demo.model.KmTheoHoaDon;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KmTheoHoaDonServices {
    private KmTheoHoaDonDAO kmTheoHoaDonDAO = new KmTheoHoaDonDAO();
    private List<KmTheoHoaDon> listKmTheoHoaDon = new ArrayList<KmTheoHoaDon>();
    public List<KmTheoHoaDon>getKmTheoHoaDonList() {
        listKmTheoHoaDon=kmTheoHoaDonDAO.getListKmTheoHoaDon();
        System.out.println(listKmTheoHoaDon);
        return listKmTheoHoaDon;
    }
    public List<KmTheoHoaDon> findByIDMaChuongTrinh (String idmaChuongTrinh){
        List<KmTheoHoaDon> listResult = new ArrayList<KmTheoHoaDon>();
        for (KmTheoHoaDon kmTheoHoaDon : listKmTheoHoaDon) {
            if(kmTheoHoaDon.getMactkm().contains(idmaChuongTrinh)){
                listResult.add(kmTheoHoaDon);
            }
        }
        return listResult;
    }
    public String addKhuyenMai(KmTheoHoaDon kmTheoHoaDon) {
        if(!checkMaChuongTrinh(kmTheoHoaDon.getMactkm())){
            kmTheoHoaDonDAO.addKhuyenMai(kmTheoHoaDon);
            return " Add Success";
        }
        return " Add Fail";
    }

    public boolean checkMaChuongTrinh(String mact) {
        for(KmTheoHoaDon kmTheoHoaDon : listKmTheoHoaDon){
            if(kmTheoHoaDon.getMactkm().equals(mact)){
                return true;
            }
        }
        return false;
    }

    public List<KmTheoHoaDon> searchChuongTrinh(String in4ChuongTrinh) {
        List<KmTheoHoaDon> result= listKmTheoHoaDon.stream()
                .filter(kmTheoHoaDon ->
                        String.valueOf(kmTheoHoaDon.getMactkm()).contains(in4ChuongTrinh) ||
                                String.valueOf(kmTheoHoaDon.getMasanpham()).contains(in4ChuongTrinh) ||
                                String.valueOf(kmTheoHoaDon.getTongtien()).contains(in4ChuongTrinh) ||
                                String.valueOf(kmTheoHoaDon.getPhantramkhuyenmai()).contains(in4ChuongTrinh)
                )
                .collect(Collectors.toList());
        return result;
    }

    public void deleteKhuyenMai(String maChuongTrinhKhuyenMai) {
        listKmTheoHoaDon.removeIf(kmTheoHoaDon -> kmTheoHoaDon.getMactkm().equals(maChuongTrinhKhuyenMai));
        kmTheoHoaDonDAO.deleteKhuyenMai(maChuongTrinhKhuyenMai);
    }
    public String updateChuongTrinhKhuyenMai (KmTheoHoaDon kmTheoHoaDon) {
        kmTheoHoaDonDAO.updateChuongTrinhKhuyenMai(kmTheoHoaDon);
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
