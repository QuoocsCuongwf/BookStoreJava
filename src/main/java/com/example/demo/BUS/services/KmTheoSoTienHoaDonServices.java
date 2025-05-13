package com.example.demo.BUS.services;

import com.example.demo.databaseAccesssObject.KmTheoSoTienHoaDonDAO;
import com.example.demo.model.KmTheoSoTienHoaDon;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KmTheoSoTienHoaDonServices {
    private KmTheoSoTienHoaDonDAO KmTheoSoTienHoaDonDAO = new KmTheoSoTienHoaDonDAO();
    private List<KmTheoSoTienHoaDon> listKmTheoSoTienHoaDon = new ArrayList<KmTheoSoTienHoaDon>();
    public List<KmTheoSoTienHoaDon>getKmTheoSoTienHoaDonList() {
        listKmTheoSoTienHoaDon=KmTheoSoTienHoaDonDAO.getListKmTheoSoTienHoaDon();
        System.out.println(listKmTheoSoTienHoaDon);
        return listKmTheoSoTienHoaDon;
    }
    public List<KmTheoSoTienHoaDon> findByIDMaChuongTrinh (String idmaChuongTrinh){
        List<KmTheoSoTienHoaDon> listResult = new ArrayList<KmTheoSoTienHoaDon>();
        for (KmTheoSoTienHoaDon khuyenMai : listKmTheoSoTienHoaDon) {
            if(khuyenMai.getMactkm().contains(idmaChuongTrinh)){
                listResult.add(khuyenMai);
            }
        }
        return listResult;
    }
    public String addKmTheoSoTienHoaDon(KmTheoSoTienHoaDon KmTheoSoTienHoaDon) {
        if(!checkMaChuongTrinh(KmTheoSoTienHoaDon.getMactkm())){
            KmTheoSoTienHoaDonDAO.addKmTheoSoTienHoaDon(KmTheoSoTienHoaDon);
            return " Add Success";
        }
        return " Add Fail";
    }

    public boolean checkMaChuongTrinh(String mact) {
        listKmTheoSoTienHoaDon = getKmTheoSoTienHoaDonList();
        for(KmTheoSoTienHoaDon kmTheoHoaDon : listKmTheoSoTienHoaDon){
            if(kmTheoHoaDon.getMactkm().equals(mact)){
                return true;
            }
        }
        return false;
    }

    public List<KmTheoSoTienHoaDon> searchChuongTrinh(String in4ChuongTrinh) {
        List<KmTheoSoTienHoaDon> result= listKmTheoSoTienHoaDon.stream()
                .filter(KmTheoSoTienHoaDon ->
                        String.valueOf(KmTheoSoTienHoaDon.getMactkm()).contains(in4ChuongTrinh) ||
                                // String.valueOf(khuyenMai.getMasanpham()).contains(in4ChuongTrinh) ||
                                String.valueOf(KmTheoSoTienHoaDon.getSotienhoadon()).contains(in4ChuongTrinh) ||
                                String.valueOf(KmTheoSoTienHoaDon.getPhantramkhuyenmai()).contains(in4ChuongTrinh) ||
                                String.valueOf(KmTheoSoTienHoaDon.getNgaybd()).contains(in4ChuongTrinh) ||
                                String.valueOf(KmTheoSoTienHoaDon.getNgaykt()).contains(in4ChuongTrinh))

                .collect(Collectors.toList());
        return result;
    }

    public void DeleteKmTheoSoTienHoaDon(String maChuongTrinhKhuyenMai) {
        listKmTheoSoTienHoaDon.removeIf(KmTheoSoTienHoaDon -> KmTheoSoTienHoaDon.getMactkm().equals(maChuongTrinhKhuyenMai));
        KmTheoSoTienHoaDonDAO.deleteKmTheoSoTienHoaDon(maChuongTrinhKhuyenMai);
    }
    public String UpdateKmTheoSoTienHoaDon (KmTheoSoTienHoaDon KmTheoSoTienHoaDon) {

        KmTheoSoTienHoaDonDAO.UpdateKmTheoSoTienHoaDon(KmTheoSoTienHoaDon);
        listKmTheoSoTienHoaDon = KmTheoSoTienHoaDonDAO.getListKmTheoSoTienHoaDon();
        return "update success";
    }
}